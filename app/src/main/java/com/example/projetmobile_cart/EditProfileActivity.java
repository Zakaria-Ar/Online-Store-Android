package com.example.projetmobile_cart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Dismiss Changes Button
        Button dismissChanges = findViewById(R.id.dismissChanges);
        dismissChanges.setOnClickListener(v -> {
            // Start the ProfileActivity
            startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
        });

        // Get current user's ID
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String userID = user.getUid();

        // Get references to the TextViews for displaying user profile information
        final TextView firstNameTextView = findViewById(R.id.firstNameResponse);
        final TextView lastNameTextView = findViewById(R.id.lastNameResponse);
        final TextView emailTextView = findViewById(R.id.emailAddressResponse);
        final TextView mobileNumberTextView = findViewById(R.id.mobileNumberResponse);
        final TextView bioTextView = findViewById(R.id.bioResponse);

        // Retrieve user profile data from Firebase Realtime Database
        FirebaseDatabase.getInstance().getReference("Users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User document exists, retrieve data and set it to the TextViews
                    String firstName = dataSnapshot.child("First Name").getValue(String.class);
                    String lastName = dataSnapshot.child("Last Name").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String mobileNumber = dataSnapshot.child("Mobile Number").getValue(String.class);
                    String bio = dataSnapshot.child("bio").getValue(String.class);

                    firstNameTextView.setText(firstName);
                    lastNameTextView.setText(lastName);
                    emailTextView.setText(email);
                    mobileNumberTextView.setText(mobileNumber);
                    bioTextView.setText(bio);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EditProfileActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });

        // Save Changes Button
        Button saveButton = findViewById(R.id.saveChanges);
        saveButton.setOnClickListener(v -> {
            // Get current user's ID
            FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
            assert user1 != null;
            String userID1 = user1.getUid();

            // Retrieve the entered user profile information
            String firstName = firstNameTextView.getText().toString();
            String lastName = lastNameTextView.getText().toString();
            String email = emailTextView.getText().toString();
            String mobileNumber = mobileNumberTextView.getText().toString();
            String bio = bioTextView.getText().toString();

            // Validate the entered information
            if (firstName.isEmpty()) {
                firstNameTextView.setError("First Name is required!");
                firstNameTextView.requestFocus();
                return;
            }
            if (lastName.isEmpty()) {
                lastNameTextView.setError("Last Name is required!");
                lastNameTextView.requestFocus();
                return;
            }
            if (mobileNumber.length() != 10) {
                mobileNumberTextView.setError("Mobile Number is incorrect!");
                mobileNumberTextView.requestFocus();
                return;
            }
            if (email.isEmpty()) {
                emailTextView.setError("Email is required");
                emailTextView.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailTextView.setError("Please enter a valid email");
                emailTextView.requestFocus();
                return;
            }

            // Update user profile data in Firebase Realtime Database
            FirebaseDatabase.getInstance().getReference("Users").child(userID1)
                    .child("First Name").setValue(firstName);
            FirebaseDatabase.getInstance().getReference("Users").child(userID1)
                    .child("Last Name").setValue(lastName);
            FirebaseDatabase.getInstance().getReference("Users").child(userID1)
                    .child("email").setValue(email);
            FirebaseDatabase.getInstance().getReference("Users").child(userID1)
                    .child("Mobile Number").setValue(mobileNumber);
            FirebaseDatabase.getInstance().getReference("Users").child(userID1)
                    .child("bio").setValue(bio);

            Toast.makeText(EditProfileActivity.this, "Profile updated successfully!", Toast.LENGTH_LONG).show();
        });
    }
}
