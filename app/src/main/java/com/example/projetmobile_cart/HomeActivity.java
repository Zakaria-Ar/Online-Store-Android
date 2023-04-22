package com.example.projetmobile_cart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        View account = (View) findViewById(R.id.menuAccount);
        account.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, AccountInformationActivity.class)));
        account = (View) findViewById(R.id.menuFavorite);
        account.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this,ShoppingBasketActivity.class)));
        account = (View) findViewById(R.id.menuCreateStore);
        account.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ImportActivity.class)));
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        String userID = null;
        if (user != null) {
            userID = user.getUid();
        }
        final TextView userName = (TextView) findViewById(R.id.textUserName);
        assert userID != null;
        firebaseFirestore.collection("User").document(userID).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                // User document exists, get data
                String fullName = documentSnapshot.getString("lastName");
                userName.setText(fullName);

            }
        })
                .addOnFailureListener(e -> userName.setText("User"));

    }
}