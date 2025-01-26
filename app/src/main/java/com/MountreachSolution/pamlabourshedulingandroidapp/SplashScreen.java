package com.MountreachSolution.pamlabourshedulingandroidapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.MountreachSolution.pamlabourshedulingandroidapp.Admin.AdminHomepage;
import com.MountreachSolution.pamlabourshedulingandroidapp.Farmer.FarmerHomepage;
import com.MountreachSolution.pamlabourshedulingandroidapp.Labour.LabourHomepage;
import com.MountreachSolution.pamlabourshedulingandroidapp.SQLiteDataBase.UserRegisterdatabase;

public class SplashScreen extends AppCompatActivity {
    UserRegisterdatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // Make the app fullscreen by hiding the status bar and navigation bar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
//        String name="ADMIN";String number="1234567890";
//        String password="ADMIN";
//        String role="admin";
//        String address="";
//        String age="";
//        String adhar="";
//
//        database=new DATABASE(SplashScreen.this);
//        database.UserRegister(name,number,address,age,adhar,password,role);


        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                boolean isLoggedIn = sharedPreferences.getBoolean("IsLoggedIn", false);
                String role = sharedPreferences.getString("Role", null);
                if (isLoggedIn) {
                    if ("Farmer".equals(role)) {
                        startActivity(new Intent(SplashScreen.this, FarmerHomepage.class));
                    } else if ("Labour".equals(role)) {
                        startActivity(new Intent(SplashScreen.this, LabourHomepage.class));
                    } else if ("admin".equals(role)) {
                        startActivity(new Intent(SplashScreen.this, AdminHomepage.class));
                    }
                } else {
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                }
                finish();

            }
        },4000);


    }
}