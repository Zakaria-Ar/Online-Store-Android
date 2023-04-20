package com.example.projetmobile_cart;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class AddToCartActivity extends AppCompatActivity {
    List<SlideModel> imageList = new ArrayList<>();
    ImageSlider imageSlider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        imageSlider = findViewById(R.id.image_slider);
        imageList.add(new SlideModel(R.drawable.iphone1, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.iphone2, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.iphone3, ScaleTypes.FIT));
        imageSlider.setImageList(imageList);
    }
}
