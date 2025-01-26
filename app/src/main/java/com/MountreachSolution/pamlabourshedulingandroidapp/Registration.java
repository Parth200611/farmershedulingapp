package com.MountreachSolution.pamlabourshedulingandroidapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.MountreachSolution.pamlabourshedulingandroidapp.SQLiteDataBase.UserRegisterdatabase;

public class Registration extends AppCompatActivity {
    EditText etname,etmobile,etage,etadharno,etpassword,etAddress;
    ProgressDialog progressDialog;
    AppCompatButton btnRegister;
    String category;
    UserRegisterdatabase database;
    int agel;


    String name,mobileno,age,adhar,password,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.white));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.white));

        category=getIntent().getStringExtra("category");
        
        etname=findViewById(R.id.etREgisterName);
        etmobile=findViewById(R.id.etREgisterMobileno);
        etAddress=findViewById(R.id.etREgisterAddress);
        etage=findViewById(R.id.etREgisterAge);
        etadharno=findViewById(R.id.etREgisterAdharnumber);
        etpassword=findViewById(R.id.etREgisterPassword);

        btnRegister=findViewById(R.id.btnRegisterButton);

        database=new UserRegisterdatabase(Registration.this);

        
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etname.getText().toString().isEmpty()) {
                    etname.setError("Please Enter the name");
                } else if (etmobile.getText().toString().isEmpty()) {
                    etmobile.setError("Please enter the mobile number");
                } else if (etmobile.getText().toString().length() != 10) {
                    etmobile.setError("Please enter Proper Mobile number ");
                } else if (etAddress.getText().toString().isEmpty()) {
                    etAddress.setError("Please enter the address");
                } else if (etage.getText().toString().isEmpty()) {
                    etage.setError("Please enter the age");

                } else if (etadharno.getText().toString().isEmpty()) {
                    etadharno.setError("Please enter adhra number for verification");
                } else if (etpassword.getText().toString().isEmpty()) {
                    etpassword.setError("Please enter username");
                } else if (etpassword.getText().toString().length() < 8) {
                    etpassword.setError("username must be at least 8 characters");
                } else if (!etpassword.getText().toString().matches(".*[A-Z].*")) {
                    etpassword.setError("Please enter at least one uppercase letter");
                } else if (!etpassword.getText().toString().matches(".*[a-z].*")) {
                    etpassword.setError("Please enter at least one lowercase letter");
                } else if (!etpassword.getText().toString().matches(".*[0-9].*")) {
                    etpassword.setError("Please enter at least one number");
                } else if (!etpassword.getText().toString().matches(".*[!,@,#,$,%,&,*].*")) {
                    etpassword.setError("Please enter at least one special symbol");
                } else {
                    progressDialog = new ProgressDialog(Registration.this);
                    progressDialog.setTitle("Creating Account");
                    progressDialog.setMessage("Wait for a while");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    name = etname.getText().toString();
                    mobileno = etmobile.getText().toString();
                    address = etAddress.getText().toString();
                    age = etage.getText().toString();
                    adhar = etadharno.getText().toString();
                    password = etpassword.getText().toString();

                    age = String.valueOf(Integer.parseInt(etage.getText().toString().trim()));
                    if (agel<14){
                        etage.setError("Age Must be Above 15");
                    }

                    // Check if the mobile number already exists
                    if (database.isUserExists(mobileno)) {
                        progressDialog.dismiss();
                        Toast.makeText(Registration.this, "Mobile number already exists. Please log in.", Toast.LENGTH_SHORT).show();
                    } else {
                        REgisterUser(name, mobileno, address, age, adhar, password, category);
                    }
                }
            }
        });


    }

    private void REgisterUser(String name, String mobileno, String address, String age, String adhar, String password, String category) {
        database.UserRegister(name, mobileno, address, age, adhar, password, category);
        progressDialog.dismiss();
        Intent i = new Intent(Registration.this,LoginActivity.class);
        startActivity(i);
    }
}