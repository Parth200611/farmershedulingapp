package com.MountreachSolution.pamlabourshedulingandroidapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Registration extends AppCompatActivity {
    EditText etname,etmobile,etage,etadharno,etpassword,etAddress;
    ProgressDialog progressDialog;
    AppCompatButton btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.white));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.white));
        
        etname=findViewById(R.id.etREgisterName);
        etmobile=findViewById(R.id.etREgisterMobileno);
        etAddress=findViewById(R.id.etREgisterAddress);
        etage=findViewById(R.id.etREgisterAge);
        etadharno=findViewById(R.id.etREgisterAdharnumber);
        etpassword=findViewById(R.id.etREgisterPassword);

        btnRegister=findViewById(R.id.btnRegisterButton);
        
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etname.getText().toString().isEmpty()){
                    etname.setError("Please Enter the name"
                    );
                } else if (etmobile.getText().toString().isEmpty()) {
                    etmobile.setError("Please enter the mobile number");
                    
                } else if (etmobile.getText().toString().length() != 10) {
                    etmobile.setError("Please enter Proper Mobile number ");
                } else if (etAddress.getText().toString().isEmpty()) {
                    etAddress.setError("Please enter the address");
                } else if (etage.getText().toString().isEmpty()) {
                    etage.setError("Please enter the age");
                    int age = Integer.parseInt(etage.getText().toString().trim());
                    if (age<14){
                        etage.setError("Age not valid");
                    }// Parse the age

                } else if (etadharno.getText().toString().isEmpty()) {
                    etadharno.setError("Please enter adhra number for verification");
                }else if (etpassword.getText().toString().isEmpty()) {
                    etpassword.setError("Please enter username");
                } else if (etpassword.getText().toString().length() < 8) {
                    etpassword.setError("username must be atlest 8  character");
                } else if (!etpassword.getText().toString().matches(".*[A-Z].*")) {
                    etpassword.setError("Please enter atlest One upper case letter");
                } else if (!etpassword.getText().toString().matches(".*[a-z].*")) {
                    etpassword.setError("Please enter atlest One lower case letter");
                } else if (!etpassword.getText().toString().matches(".*[0-9].*")) {
                    etpassword.setError("Please enter atlest One number");
                } else if (!etpassword.getText().toString().matches(".*[!,@,#,$,%,&,*].*")) {
                    etpassword.setError("Please enter atlest One special symbol");
                }
                else {
                    progressDialog=new ProgressDialog(Registration.this);
                    progressDialog.setTitle("Creating Account");
                    progressDialog.setMessage("Wait for a while");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                }
            }
        });

    }
}