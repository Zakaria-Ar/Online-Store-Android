package com.example.projetmobile_cart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up click listeners for buttons and text views
        TextView register = findViewById(R.id.goToSignUpActivity);
        register.setOnClickListener(this);

        Button login = findViewById(R.id.login);
        login.setOnClickListener(this);

        editTextEmail = findViewById(R.id.emailAddress);
        editTextPassword = findViewById(R.id.password);

        progressBar = findViewById(R.id.progressbar);
        mAuth = FirebaseAuth.getInstance();

        TextView forgotPassword = findViewById(R.id.resetPassword);
        forgotPassword.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        // Handle click events for different views
        switch (v.getId()) {
            case R.id.goToSignUpActivity:
                // Start SignUpActivity when register TextView is clicked
                startActivity(new Intent(this, SignUpActivity.class));
                break;
            case R.id.login:
                // Perform user login when login button is clicked
                userLogin();
                break;
            case R.id.resetPassword:
                // Start ForgotPasswordActivity when resetPassword TextView is clicked
                startActivity(new Intent(this, ForgotPasswordActivity.class));
                break;
        }
    }

    private void userLogin() {
        // Retrieve user input for email and password
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Validate user input
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
            editTextPassword.setError("Password is required !");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 8) {
            editTextPassword.setError("Min password length is 8 characters !");
            editTextPassword.requestFocus();
            return;
        }

        // Display progress bar while performing login
        progressBar.setVisibility(View.VISIBLE);

        // Authenticate user with provided email and password
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                assert user != null;
                if (user.isEmailVerified()) {
                    // Redirect to user profile if email is verified
                    startActivity(new Intent(LoginActivity.this, PrincipalePageActivity.class));
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    // Send email verification and display a message
                    user.sendEmailVerification();
                    Toast.makeText(LoginActivity.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            } else {
                // Display an error message if login fails
                Toast.makeText(LoginActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
