package com.example.projetmobile_cart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;

import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class StartActivity extends AppCompatActivity {
    private ImageView iconImage;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Initialize UI elements
        iconImage = findViewById(R.id.icon_image);
        linearLayout = findViewById(R.id.linearLayout);
        Button register = findViewById(R.id.register);
        Button login = findViewById(R.id.login2);
        linearLayout.animate().alpha(0f).setDuration(10);

        // Animation for the iconImage
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -1000);
        animation.setDuration(1000);
        animation.setFillAfter(false);
        animation.setAnimationListener(new MyAnimationListener());
        iconImage.setAnimation(animation);

        // Set click listeners for the register and login buttons
        register.setOnClickListener(v -> startActivity(new Intent(StartActivity.this, SignUpActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP)));
        login.setOnClickListener(v -> startActivity(new Intent(StartActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP)));
    }

    // AnimationListener class for the iconImage animation
    private class MyAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {
            // Not implemented
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            // Animation ended, clear the animation and hide the iconImage
            iconImage.clearAnimation();
            iconImage.setVisibility(View.INVISIBLE);

            // Fade in the linearLayout
            linearLayout.animate().alpha(1f).setDuration(1000);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // Not implemented
        }
    }
}
