package com.example.projetmobile_cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

        Button logout = (Button) findViewById(R.id.signout);
        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        });
        Button viewStore = (Button) findViewById(R.id.viewStore);
        viewStore.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, ImportActivity.class));
        });


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String userID = user.getUid();

        final TextView firstNameTextView = (TextView) findViewById(R.id.firstNameResponse);
        final TextView lastNameTextView = (TextView) findViewById(R.id.lastNameResponse);
        final TextView emailTextView = (TextView) findViewById(R.id.emailAddressResponse);
        final TextView mobileNumberTextView = (TextView) findViewById(R.id.mobileNumberResponse);
        final TextView bioTextView = (TextView) findViewById(R.id.bioResponse);
        FirebaseDatabase.getInstance().getReference("Users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User document exists, get data
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
                Toast.makeText(ProfileActivity.this, "Something Wrong happened !", Toast.LENGTH_LONG).show();
            }
        });
    }
}
