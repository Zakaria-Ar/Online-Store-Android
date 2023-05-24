package com.example.projetmobile_cart;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AddToCartActivity extends AppCompatActivity {
    TextView detailDesc, detailTitle, detailLang;
    ImageView detailImage;
    String key = "";
    String imageUrl = "";
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private ImageView imageViewFavorites;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        // Retrieve the favorites icon reference
        imageViewFavorites = findViewById(R.id.imageView2);

        // Initialize views
        detailDesc = findViewById(R.id.detailDesc);
        detailTitle = findViewById(R.id.detailTitle);
        detailImage = findViewById(R.id.productImage);
        detailLang = findViewById(R.id.productPrice);

        // Retrieve data from intent extras
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailDesc.setText(bundle.getString("Description"));
            detailTitle.setText(bundle.getString("Title"));
            detailLang.setText(bundle.getString("Price") + " DH");
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }

        // Check if the product already exists in favorites
        checkFavoriteStatus();

        // Favorites icon click listener
        imageViewFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFavoriteStatus();
            }
        });

        // Go back button click listener
        ImageView goBackImageView = findViewById(R.id.goBack);
        goBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Go back to the previous activity
            }
        });

        // Add to cart button click listener
        Button addToCartButton = findViewById(R.id.addToCartBtn);
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
    }

    private void checkFavoriteStatus() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference("favorites");

        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userFavoritesRef = favoritesRef.child(userId);

            Query query = userFavoritesRef.orderByChild("dataTitle").equalTo(detailTitle.getText().toString());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Product already exists in favorites, so set the selected color
                        imageViewFavorites.setImageResource(R.drawable.img_2);
                    } else {
                        // Product doesn't exist in favorites, so set the unselected color
                        imageViewFavorites.setImageResource(R.drawable.img_1);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle database error if necessary
                }
            });
        }
    }

    private void toggleFavoriteStatus() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference("favorites");

        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userFavoritesRef = favoritesRef.child(userId);

            Query query = userFavoritesRef.orderByChild("dataTitle").equalTo(detailTitle.getText().toString());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Product already exists in favorites, so remove it
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue();
                        }
                        Toast.makeText(AddToCartActivity.this, "Removed from favorites", Toast.LENGTH_SHORT).show();
                        // Change the color of the favorites icon to indicate it's not selected
                        imageViewFavorites.setImageResource(R.drawable.img_1);
                    } else {
                        // Product doesn't exist in favorites, so add it
                        // Generate a unique favoriteId for the new favorite
                        String favoriteId = userFavoritesRef.push().getKey();

                        // Create a Favorite object with the product details
                        Favorite favorite = new Favorite();
                        favorite.setDataDesc(detailDesc.getText().toString());
                        favorite.setDataImage(imageUrl);
                        favorite.setDataTitle(detailTitle.getText().toString());

                        String price = detailLang.getText().toString();
                        if (!price.isEmpty()) {
                            favorite.setDataPrice(price + " DH");
                        } else {
                            favorite.setDataPrice(""); // Set an empty string if no price is available
                        }

                        // Add the favorite to the user's favorites list
                        userFavoritesRef.child(favoriteId).setValue(favorite)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(AddToCartActivity.this, "Added to favorites", Toast.LENGTH_SHORT).show();
                                        // Change the color of the favorites icon to indicate it's selected
                                        imageViewFavorites.setImageResource(R.drawable.img_2);
                                    }
                                });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle database error if necessary
                }
            });
        }
    }

    // Function to add the product to the cart
    private void addToCart() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference("Basket");

        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userFavoritesRef = favoritesRef.child(userId);

            // Check if the product already exists in the cart
            Query query = userFavoritesRef.orderByChild("dataTitle").equalTo(detailTitle.getText().toString());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Toast.makeText(AddToCartActivity.this, "Product already added to Basket", Toast.LENGTH_SHORT).show();
                    } else {
                        // Generate a unique favoriteId for the new cart item
                        String favoriteId = userFavoritesRef.push().getKey();

                        // Create a Basket object with the product details
                        Basket basket = new Basket();
                        basket.setDataDesc(detailDesc.getText().toString());
                        basket.setDataImage(imageUrl);
                        basket.setDataTitle(detailTitle.getText().toString());

                        String price = detailLang.getText().toString();
                        if (!price.isEmpty()) {
                            basket.setDataPrice(price + " DH");
                        } else {
                            basket.setDataPrice("No price available"); // Set a default message when no price is available
                        }

                        // Add the cart item to the user's cart
                        userFavoritesRef.child(favoriteId).setValue(basket)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(AddToCartActivity.this, "Added to Basket", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle database error if necessary
                }
            });
        }
    }
}
