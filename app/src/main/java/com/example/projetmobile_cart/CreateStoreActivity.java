package com.example.projetmobile_cart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class CreateStoreActivity extends AppCompatActivity {
    ImageView imageView;
    ImageView imageView2;

    ImageView imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_store);
        imageView = findViewById(R.id.imgcrtstore);
        imageView2 = findViewById(R.id.imageView2);
        imageButton = findViewById(R.id.imageButton);
        imageView.setOnClickListener(view -> startActivity(new Intent(CreateStoreActivity.this, StoreNameActivity.class)));
        imageButton.setOnClickListener(view -> startActivity(new Intent(CreateStoreActivity.this, StoreNameActivity.class)));
        imageView2.setOnClickListener(view -> startActivity(new Intent(CreateStoreActivity.this, StoreNameActivity.class)));
    }


}