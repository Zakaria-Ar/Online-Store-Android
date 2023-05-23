package com.example.projetmobile_cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Set click listeners for logout, viewStore, and editProfile buttons
        Button logout = findViewById(R.id.signout);
        logout.setOnClickListener(v -> {
            // Sign out the current user and navigate to the login activity
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        });

        Button viewStore = findViewById(R.id.viewStore);
        viewStore.setOnClickListener(v -> {
            // Navigate to the ImportActivity to view the store
            startActivity(new Intent(ProfileActivity.this, ImportActivity.class));
        });

        Button editProfile = findViewById(R.id.editProfile);
        editProfile.setOnClickListener(v -> {
            // Navigate to the EditProfileActivity to edit the user profile
            startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
        });

        // Fetch and display user profile information from Firebase Realtime Database
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String userID = user.getUid();

        final TextView firstNameTextView = findViewById(R.id.firstNameResponse);
        final TextView lastNameTextView = findViewById(R.id.lastNameResponse);
        final TextView emailTextView = findViewById(R.id.emailAddressResponse);
        final TextView mobileNumberTextView = findViewById(R.id.mobileNumberResponse);
        final TextView bioTextView = findViewById(R.id.bioResponse);

        FirebaseDatabase.getInstance().getReference("Users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User document exists, retrieve user profile data
                    String firstName = dataSnapshot.child("First Name").getValue(String.class);
                    String lastName = dataSnapshot.child("Last Name").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String mobileNumber = dataSnapshot.child("Mobile Number").getValue(String.class);
                    String bio = dataSnapshot.child("bio").getValue(String.class);

                    // Set the retrieved data to corresponding TextViews
                    firstNameTextView.setText(firstName);
                    lastNameTextView.setText(lastName);
                    emailTextView.setText(email);
                    mobileNumberTextView.setText(mobileNumber);
                    bioTextView.setText(bio);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Display an error message if fetching user data fails
                Toast.makeText(ProfileActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
