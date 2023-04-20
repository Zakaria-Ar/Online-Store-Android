package com.example.projetmobile_cart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AccountInformationActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_information);

        Button logout = (Button) findViewById(R.id.signout);
        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(AccountInformationActivity.this, LoginActivity.class));
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        assert user != null;
        String userID = user.getUid();

        final TextView firstNameTextView = (TextView) findViewById(R.id.firstName);
        final TextView lastNameTextView = (TextView) findViewById(R.id.lastName);
        final TextView emailTextView = (TextView) findViewById(R.id.emailAddress);
        final TextView mobileNumberTextView = (TextView) findViewById(R.id.mobileNumber);

        firebaseFirestore.collection("User").document(userID).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                // User document exists, get data
                String firstName = documentSnapshot.getString("firstName");
                String lastName = documentSnapshot.getString("lastName");
                String email = documentSnapshot.getString("email");
                String mobileNumber = documentSnapshot.getString("number");

                firstNameTextView.setText(firstName);
                lastNameTextView.setText(lastName);
                emailTextView.setText(email);
                mobileNumberTextView.setText(mobileNumber);

            }
        })
                .addOnFailureListener(e -> Toast.makeText(AccountInformationActivity.this, "Something Wrong happened !", Toast.LENGTH_LONG).show());
    }
}