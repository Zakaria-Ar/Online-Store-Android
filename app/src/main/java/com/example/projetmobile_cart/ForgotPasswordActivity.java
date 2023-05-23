package com.example.projetmobile_cart;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailEditText;
    private ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Initialize UI elements
        emailEditText = (EditText) findViewById(R.id.emailAddress);
        Button resetPasswordButton = (Button) findViewById(R.id.resetPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        // Get instance of FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // Set a click listener for the reset password button
        resetPasswordButton.setOnClickListener(v -> resetPassword());
    }

    private void resetPassword(){
        String email = emailEditText.getText().toString().trim();

        // Perform email validation
        if(email.isEmpty()){
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Please enter a valid email");
            emailEditText.requestFocus();
            return;
        }

        // Display progress bar
        progressBar.setVisibility(View.VISIBLE);

        // Send password reset email using FirebaseAuth
        auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                // Password reset email sent successfully
                Toast.makeText(ForgotPasswordActivity.this, "Check your email to reset your password", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
            }else{
                // An error occurred while sending the password reset email
                Toast.makeText(ForgotPasswordActivity.this, "Try again! Something wrong happened!", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
