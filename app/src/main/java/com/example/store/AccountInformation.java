package com.example.store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AccountInformation extends AppCompatActivity {

    private FirebaseUser user;
    FirebaseFirestore firebaseFirestore;
    private String userID;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_information);

        logout = (Button) findViewById(R.id.signout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AccountInformation.this, LoginActivity.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userID = user.getUid();

        final TextView fullNameTextView = (TextView) findViewById(R.id.fullName);
        final TextView emailTextView = (TextView) findViewById(R.id.emailAddress);
        final TextView mobileNumberTextView = (TextView) findViewById(R.id.mobileNumber);

        firebaseFirestore.collection("User").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()  {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            // User document exists, get data
                            String fullName = documentSnapshot.getString("name");
                            String email = documentSnapshot.getString("email");
                            String mobileNumber = documentSnapshot.getString("number");

                            fullNameTextView.setText(fullName);
                            emailTextView.setText(email);
                            mobileNumberTextView.setText(mobileNumber);

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AccountInformation.this, "Something Wrong happened !", Toast.LENGTH_LONG).show();
                    }
                });
    }
}