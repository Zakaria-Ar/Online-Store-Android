package com.example.projetmobile_cart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {
    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        View account = (View) findViewById(R.id.menuAccount);
        account.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ProfileActivity.class)));
        account = (View) findViewById(R.id.menuFavorite);
        account.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this,ShoppingBasketActivity.class)));
        account = (View) findViewById(R.id.menuCreateStore);
        account.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ImportActivity.class)));
        account = (View) findViewById(R.id.menuSettings);
        account.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, AccountInformationActivity.class)));

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String userID = user.getUid();

        final TextView userName = (TextView) findViewById(R.id.textUserName);
        FirebaseDatabase.getInstance().getReference("Users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User document exists, get data
                    String Name = dataSnapshot.child("Last Name").getValue(String.class);
                    userName.setText(Name);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, "UserName is Anouar by default !", Toast.LENGTH_LONG).show();
            }
        });

    }
}