package com.example.projetmobile_cart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword, editTextLastName, editTextFirstName, editTextRePassword, editTextMobileNumber;
    private FirebaseAuth mAuth;
    private DatabaseReference mRootRef;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize UI elements
        editTextEmail = findViewById(R.id.emailAddress);
        editTextRePassword = findViewById(R.id.rePassword);
        editTextFirstName = findViewById(R.id.firstName);
        editTextLastName = findViewById(R.id.lastName);
        editTextMobileNumber = findViewById(R.id.mobileNumber);
        editTextPassword = findViewById(R.id.password);

        // Initialize Firebase instances
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);

        // Set click listener for the login text view
        TextView login = findViewById(R.id.goToLoginActivity);
        login.setOnClickListener(v -> startActivity(new Intent(SignUpActivity.this, LoginActivity.class)));

        // Set click listener for the signup button
        Button signUp = findViewById(R.id.signup);
        signUp.setOnClickListener(v -> {
            // Retrieve user input
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String rePassword = editTextRePassword.getText().toString().trim();
            String mobileNumber = editTextMobileNumber.getText().toString().trim();
            String firstName = editTextFirstName.getText().toString().trim();
            String lastName = editTextLastName.getText().toString().trim();

            // Validate user input
            if (firstName.isEmpty()) {
                editTextFirstName.setError("First Name is required!");
                editTextFirstName.requestFocus();
                return;
            }
            if (lastName.isEmpty()) {
                editTextLastName.setError("Last Name is required!");
                editTextLastName.requestFocus();
                return;
            }
            if (mobileNumber.length() != 10) {
                editTextMobileNumber.setError("Mobile Number is incorrect!");
                editTextMobileNumber.requestFocus();
                return;
            }
            if (email.isEmpty()) {
                editTextEmail.setError("Email is required");
                editTextEmail.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTextEmail.setError("Please enter a valid email");
                editTextEmail.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                editTextPassword.setError("Password is required!");
                editTextPassword.requestFocus();
                return;
            }
            if (password.length() < 8) {
                editTextPassword.setError("Min password length is 8 characters!");
                editTextPassword.requestFocus();
                return;
            }
            if (!rePassword.equals(password)) {
                editTextRePassword.setError("Password is incorrect");
                editTextRePassword.requestFocus();
                return;
            } else {
                // Register the user
                registerUser(firstName, lastName, email, password, mobileNumber);
            }
        });
    }

    private void registerUser(final String firstName, final String lastName, final String email, String password, final String mobileNumber) {
        pd.setMessage("Please Wait!");
        pd.show();

        // Create a new user with email and password in Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
            // User creation is successful
            HashMap<String, Object> map = new HashMap<>();
            map.put("Last Name", lastName);
            map.put("email", email);
            map.put("First Name", firstName);
            map.put("id", Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
            map.put("Mobile Number", mobileNumber);
            map.put("bio", "");
            map.put("imageurl", "default");

            // Save user data in Firebase Realtime Database
            mRootRef.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    pd.dismiss();

                    // Redirect to the main activity after successful registration
                    Intent intent = new Intent(SignUpActivity.this, PrincipalePageActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            });
        }).addOnFailureListener(e -> {
            pd.dismiss();
            Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}
