package com.MountreachSolution.pamlabourshedulingandroidapp.Farmer;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.MountreachSolution.pamlabourshedulingandroidapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FarmerHomepage extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_farmer_homepage);

        getWindow().setStatusBarColor(ContextCompat.getColor(FarmerHomepage.this,R.color.green));
        getWindow().setNavigationBarColor(ContextCompat.getColor(FarmerHomepage.this,R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        bottomNavigationView = findViewById(R.id.bottomnevigatiomuserhome);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.FarmerHome);

    }

    HomeFragment homeFragment= new HomeFragment();
    PostFragment postFragment= new PostFragment();
    ProfilFragment profilFragment = new ProfilFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.FarmerHome){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,homeFragment).commit();
        }else if(item.getItemId()==R.id.FarmerPostwork){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,postFragment).commit();
        } else if(item.getItemId()==R.id.FarmerProfil){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,profilFragment).commit();
        }
        return true;
    }
}