package com.example.projetmobile_cart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CreateStoreActivity extends AppCompatActivity {
    ImageView imgview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_store);
        imgview = findViewById(R.id.imgcrtstore);
        imgview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Créer l'intention pour passer à la seconde activité
                Intent intent = new Intent(CreateStoreActivity.this, StoreNameActivity.class);

                // Démarrer la seconde activité
                startActivity(intent);
            }
        });
    }


}