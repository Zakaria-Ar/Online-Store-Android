package com.example.projetmobile_cart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        Button dismissChanges = (Button) findViewById(R.id.dismissChanges);
        dismissChanges.setOnClickListener(v -> {
            startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
        });
    }
}