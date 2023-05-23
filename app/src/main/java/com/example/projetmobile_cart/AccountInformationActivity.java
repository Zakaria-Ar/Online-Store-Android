package com.example.projetmobile_cart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_information);

        // Logout button setup
        Button logout = (Button) findViewById(R.id.signout);
        logout.setOnClickListener(v -> {
            // Sign out the user
            FirebaseAuth.getInstance().signOut();

            // Redirect to the login screen
            startActivity(new Intent(AccountInformationActivity.this, LoginActivity.class));
        });

        // Get the current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String userID = user.getUid();

        // TextViews for displaying user information
        final TextView firstNameTextView = (TextView) findViewById(R.id.firstName);
        final TextView lastNameTextView = (TextView) findViewById(R.id.lastName);
        final TextView emailTextView = (TextView) findViewById(R.id.emailAddress);
        final TextView mobileNumberTextView = (TextView) findViewById(R.id.mobileNumber);

        // Retrieve user information from the Firebase Realtime Database
        FirebaseDatabase.getInstance().getReference("Users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User document exists, get data
                    String firstName = dataSnapshot.child("First Name").getValue(String.class);
                    String lastName = dataSnapshot.child("Last Name").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String mobileNumber = dataSnapshot.child("Mobile Number").getValue(String.class);

                    // Display user information in the TextViews
                    firstNameTextView.setText(firstName);
                    lastNameTextView.setText(lastName);
                    emailTextView.setText(email);
                    mobileNumberTextView.setText(mobileNumber);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Display an error message if data retrieval is unsuccessful
                Toast.makeText(AccountInformationActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
