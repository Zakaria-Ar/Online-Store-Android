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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBasketActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BasketAdapter basketAdapter;
    private List<Basket> basketList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_basket);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize basketList
        basketList = new ArrayList<>();

        // Initialize basketAdapter
        basketAdapter = new BasketAdapter(basketList);
        recyclerView.setAdapter(basketAdapter);

        // Calculate and display the total price of items in the basket
        calculateTotalPrice();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Load basket items from Firebase Realtime Database
        loadBasketItems();
    }

    private void loadBasketItems() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String userId = user.getUid();

            // Get the reference to the user's basket items in the Firebase Realtime Database
            DatabaseReference userBasketRef = FirebaseDatabase.getInstance().getReference("Basket")
                    .child(userId);

            userBasketRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    basketList.clear();

                    // Iterate over the basket items and add them to the basketList
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Basket basket = snapshot.getValue(Basket.class);
                        basketList.add(basket);
                    }

                    basketAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error if needed
                }
            });
        }
    }

    private void calculateTotalPrice() {
        DatabaseReference basketRef = FirebaseDatabase.getInstance().getReference("Basket");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userBasketRef = basketRef.child(userId);

            userBasketRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int totalPrice = 0;

                    // Iterate over the basket items and calculate the total price
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Basket basket = snapshot.getValue(Basket.class);
                        if (basket != null) {
                            // Extract the price from the data and convert it to an integer
                            String priceString = basket.getDataPrice().replaceAll("[^\\d.]", ""); // Remove non-digit characters
                            int price = 0;

                            try {
                                price = Integer.parseInt(priceString);
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }

                            totalPrice += price;
                        }
                    }

                    // Display the total price in the appropriate TextView
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

