package com.example.craveapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {


    private static final long DELAY_TIME_MS = 3500; // Delay time in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int sdkVersion = Build.VERSION.SDK_INT;
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.beginning_screen);
        System.out.println(sdkVersion);

        LottieAnimationView animationView = findViewById(R.id.splash_lotti);
        animationView.setAnimation(R.raw.burger);
        animationView.playAnimation();

        // Delay the launch of the second activity using a Handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Launch the second activity
                Intent intent = new Intent(MainActivity.this, OnBoardingActivity.class);
                startActivity(intent);
                finish(); // Finish the current activity to prevent going back to it
            }
        }, DELAY_TIME_MS);
    }
}