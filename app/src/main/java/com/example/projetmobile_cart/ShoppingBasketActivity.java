package com.example.projetmobile_cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBasketActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavoriteAdapter favoriteAdapter;
    private List<Favorite> favoriteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_basket);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize favoriteList
        favoriteList = new ArrayList<>();

        // Initialize favoriteAdapter
        favoriteAdapter = new FavoriteAdapter(favoriteList);
        recyclerView.setAdapter(favoriteAdapter);
        calculateTotalPrice();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Load favorite items from Firebase Realtime Database
        loadFavoriteItems();
    }

    private void loadFavoriteItems() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String userId = user.getUid();

            DatabaseReference userFavoritesRef = FirebaseDatabase.getInstance().getReference("favorites")
                    .child(userId);

            userFavoritesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    favoriteList.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Favorite favorite = snapshot.getValue(Favorite.class);
                        favoriteList.add(favorite);
                    }

                    favoriteAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error if needed
                }
            });
        }
    }
    private void calculateTotalPrice() {
        DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference("favorites");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userFavoritesRef = favoritesRef.child(userId);

            userFavoritesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int totalPrice = 0;

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Favorite favorite = snapshot.getValue(Favorite.class);
                        String priceString = favorite != null ? favorite.getDataPrice() : "0";
                        int price = 0;

                        try {
                            price = Integer.parseInt(priceString);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }

                        totalPrice += price;
                    }

                    TextView prixTotalTextView = findViewById(R.id.prix_total);
                    prixTotalTextView.setText(String.valueOf(totalPrice) + " DH");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle database error if necessary
                }
            });
        }
    }

}
