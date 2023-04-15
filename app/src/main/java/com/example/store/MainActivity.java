package com.example.store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.store.databinding.ActivityHomeBinding;
import com.example.store.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private TextView login;
    private EditText editTextEmail, editTextPassword,editTextLastName, editTextFirstName,editTextRePassword,editTextMobileNumber;
    private Button signUp;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (TextView) findViewById(R.id.goToLoginActivity);
        login.setOnClickListener(this::onClick);

        signUp = (Button) findViewById(R.id.signup);
        signUp.setOnClickListener(this::onClick);

        editTextEmail = (EditText) findViewById(R.id.emailAddress);
        editTextRePassword = (EditText) findViewById(R.id.rePassword);
        editTextFirstName = (EditText) findViewById(R.id.firstName);
        editTextLastName = (EditText) findViewById(R.id.lastName);
        editTextMobileNumber = (EditText) findViewById(R.id.mobileNumber);
        editTextPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.goToLoginActivity:
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case R.id.signup:
                userSignUp();
                break;
        }
    }
    private void userSignUp(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String rePassword = editTextRePassword.getText().toString().trim();
        String mobileNumber = editTextMobileNumber.getText().toString().trim();
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();

        if(firstName.isEmpty()){
            editTextFirstName.setError("First Name is required !");
            editTextFirstName.requestFocus();
            return;
        }
        if(lastName.isEmpty()){
            editTextLastName.setError("Last Name is required !");
            editTextLastName.requestFocus();
            return;
        }
        if(mobileNumber.length() != 10){
            editTextMobileNumber.setError("Mobile Number is incorrect !");
            editTextMobileNumber.requestFocus();
            return;
        }

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
        if(!rePassword.equals(password)){
            editTextRePassword.setError("Password is incorrect");
            editTextRePassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                firebaseFirestore.collection("User")
                        .document(FirebaseAuth.getInstance().getUid())
                        .set(new UserModel(firstName,lastName,mobileNumber,email,password));
                progressBar.setVisibility(View.INVISIBLE);
            }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }
}