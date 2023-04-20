package com.example.projetmobile_cart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StoreNameActivity extends AppCompatActivity {
     Button btnNext ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_name);
        btnNext = findViewById(R.id.button_next);
        btnNext.setOnClickListener(view -> {
            Intent intent = new Intent(StoreNameActivity.this, StoreFormActivity.class);
            startActivity(intent);
        });





    }
}