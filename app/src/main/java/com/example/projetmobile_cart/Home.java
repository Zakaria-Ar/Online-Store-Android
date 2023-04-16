package com.example.projetmobile_cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Home extends AppCompatActivity {
    private View account;
    private FirebaseUser user;
    FirebaseFirestore firebaseFirestore;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        account = (View) findViewById(R.id.menuAccount);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,AccountInformation.class));
            }
        });
        account = (View) findViewById(R.id.menuFavorite);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,ShoppingBasketActivity.class));
            }
        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userID = user.getUid();
        final TextView userName = (TextView) findViewById(R.id.textUserName);
        firebaseFirestore.collection("User").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()  {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            // User document exists, get data
                            String fullName = documentSnapshot.getString("lastName");
                            userName.setText(fullName);

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        userName.setText("User");
                    }
                });

    }
}