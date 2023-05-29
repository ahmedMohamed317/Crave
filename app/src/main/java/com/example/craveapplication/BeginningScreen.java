package com.example.craveapplication;

import android.os.Bundle;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;

import androidx.appcompat.app.AppCompatActivity;


public class BeginningScreen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beginning_screen);
        LottieAnimationView animationView = findViewById(R.id.splash_lotti);
        animationView.setAnimation(R.raw.burger);
        animationView.playAnimation();
    }
    }


