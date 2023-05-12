package com.example.projetmobile_cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AddToCartActivity extends AppCompatActivity {
    List<SlideModel> imageList = new ArrayList<>();
    ImageSlider imageSlider;

    TextView detailDesc, detailTitle,detailLang;
    ImageView detailImage;
    //FloatingActionButton deleteButton,editButton;
    String key = "";
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        imageSlider = findViewById(R.id.image_slider);
        imageList.add(new SlideModel(R.drawable.iphone1, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.iphone2, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.iphone3, ScaleTypes.FIT));
        imageSlider.setImageList(imageList);
        detailDesc = findViewById(R.id.detailDesc);
        detailTitle = findViewById(R.id.detailTitle);
        detailImage = findViewById(R.id.productImage);
        detailLang = findViewById(R.id.productPrice);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailDesc.setText(bundle.getString("Description"));
            detailTitle.setText(bundle.getString("Title"));
            detailLang.setText(bundle.getString("Price"));

            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }
        /*deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products");
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(key).removeValue();
                        Toast.makeText(AddToCartActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), ImportActivity.class));
                        finish();
                    }
                });
            }
        });
        /editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddToCartActivity.this, UpdateActivity.class)
                        .putExtra("Title",detailTitle.getText().toString())
                        .putExtra("Description",detailDesc.getText().toString())
                        .putExtra("Language",detailLang.getText().toString())
                        .putExtra("Image",imageUrl)
                        .putExtra("Key",key);
                startActivity(intent);
            }
        });*/
    }
}
