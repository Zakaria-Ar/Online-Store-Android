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
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView register = (TextView) findViewById(R.id.goToSignUpActivity);
        register.setOnClickListener(this);

        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.emailAddress);
        editTextPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        mAuth = FirebaseAuth.getInstance();

        TextView forgotPassword = (TextView) findViewById(R.id.resetPassword);
        forgotPassword.setOnClickListener(this);
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.goToSignUpActivity:
                startActivity(new Intent(this,SignUpActivity.class));
                break;
            case R.id.login:
                userLogin();
                break;
            case R.id.resetPassword:
                startActivity(new Intent(this, ForgotPasswordActivity.class));
                break;
        }
    }
    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required !");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 8){
            editTextPassword.setError("Min password length is 8 characters !");
            editTextPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                assert user != null;
                if(user.isEmailVerified()) {
                    //redirect to user profile
                    startActivity(new Intent(LoginActivity.this, PrincipalePageActivity.class));
                    progressBar.setVisibility(View.INVISIBLE);
                }else{
                    user.sendEmailVerification();
                    Toast.makeText(LoginActivity.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }else {
                Toast.makeText(LoginActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}