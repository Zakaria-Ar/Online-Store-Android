package com.example.projetmobile_cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AccountInformationActivity extends AppCompatActivity {

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
                startActivity(new Intent(AccountInformationActivity.this, LoginActivity.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userID = user.getUid();

        final TextView firstNameTextView = (TextView) findViewById(R.id.firstName);
        final TextView lastNameTextView = (TextView) findViewById(R.id.lastName);
        final TextView emailTextView = (TextView) findViewById(R.id.emailAddress);
        final TextView mobileNumberTextView = (TextView) findViewById(R.id.mobileNumber);

        firebaseFirestore.collection("User").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()  {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
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
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AccountInformationActivity.this, "Something Wrong happened !", Toast.LENGTH_LONG).show();
                    }
                });
    }
}