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
    TextView detailDesc, detailTitle,detailLang;
    ImageView detailImage;
    String key = "";
    String imageUrl = "";
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        detailDesc = findViewById(R.id.detailDesc);
        detailTitle = findViewById(R.id.detailTitle);
        detailImage = findViewById(R.id.productImage);
        detailLang = findViewById(R.id.productPrice);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailDesc.setText(bundle.getString("Description"));
            detailTitle.setText(bundle.getString("Title"));
            detailLang.setText(bundle.getString("Price"));
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }

        ImageView imageViewFavorites = findViewById(R.id.imageView2);
        imageViewFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToFavorites();
            }
        });
        ImageView goBackImageView = findViewById(R.id.goBack);
        goBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event here
                onBackPressed(); // Go back to the previous activity
            }
        });
        Button addToCartButton = findViewById(R.id.addToCartBtn);
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
    }
    private void addToFavorites() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference("favorites");

        if (user != null) {
            String userId = user.getUid();

            DatabaseReference userFavoritesRef = favoritesRef.child(userId);

            // Check if the product already exists in favorites
            Query query = userFavoritesRef.orderByChild("dataTitle").equalTo(detailTitle.getText().toString());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Toast.makeText(AddToCartActivity.this, "Product already added to favorites", Toast.LENGTH_SHORT).show();
                    } else {
                        String favoriteId = userFavoritesRef.push().getKey();

                        Favorite favorite = new Favorite();
                        favorite.setDataDesc(detailDesc.getText().toString());
                        favorite.setDataImage(imageUrl);
                        favorite.setDataPrice(detailLang.getText().toString());
                        favorite.setDataTitle(detailTitle.getText().toString());

                        userFavoritesRef.child(favoriteId).setValue(favorite)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(AddToCartActivity.this, "Added to favorites", Toast.LENGTH_SHORT).show();
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

    private void addToCart() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference("Basket");

        if (user != null) {
            String userId = user.getUid();

            DatabaseReference userFavoritesRef = favoritesRef.child(userId);

            // Check if the product already exists in favorites
            Query query = userFavoritesRef.orderByChild("dataTitle").equalTo(detailTitle.getText().toString());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Toast.makeText(AddToCartActivity.this, "Product already added to Basket", Toast.LENGTH_SHORT).show();
                    } else {
                        String favoriteId = userFavoritesRef.push().getKey();

                        Basket basket = new Basket();
                        basket.setDataDesc(detailDesc.getText().toString());
                        basket.setDataImage(imageUrl);
                        basket.setDataPrice(detailLang.getText().toString());
                        basket.setDataTitle(detailTitle.getText().toString());

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
