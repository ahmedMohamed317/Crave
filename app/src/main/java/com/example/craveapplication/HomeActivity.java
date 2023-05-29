package com.example.craveapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavBar);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.search:

                    navController.navigate(R.id.searchFragment);

                    return true;
                case R.id.favourite:
                    navController.navigate(R.id.favoriteFragment);
                    return true;
                case R.id.profile:
                    navController.navigate(R.id.profileFragment);
                    return true;
                case R.id.home:
                    navController.navigate(R.id.homeFragment);
                    return true;

                default:
                    navController.navigate(R.id.profileFragment);
                    return false;
            }
        });


    }
}