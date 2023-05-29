package com.example.craveapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.google.firebase.FirebaseApp;

public class RegisterationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_registeration);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_regsiteration);
        NavigationUI.setupActionBarWithNavController(this, navController);
    }

//    // Add the following method to handle Up navigation
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_regsiteration);
//        return navController.navigateUp() || super.onSupportNavigateUp();
//    }
}
