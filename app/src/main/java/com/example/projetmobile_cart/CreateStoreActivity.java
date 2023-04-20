package com.example.projetmobile_cart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CreateStoreActivity extends AppCompatActivity {
    ImageView imgview;
    ImageView imageView2;

    ImageView imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_store);
        imgview = findViewById(R.id.imgcrtstore);
        imageView2 = findViewById(R.id.imageView2);
        imageButton = findViewById(R.id.imageButton);
        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateStoreActivity.this, StoreNameActivity.class));
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateStoreActivity.this, StoreNameActivity.class));
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateStoreActivity.this, StoreNameActivity.class));
            }
        });
    }


}