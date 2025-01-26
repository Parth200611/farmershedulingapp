package com.MountreachSolution.pamlabourshedulingandroidapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;

import com.MountreachSolution.pamlabourshedulingandroidapp.Admin.AdminHomepage;
import com.MountreachSolution.pamlabourshedulingandroidapp.Farmer.FarmerHomepage;
import com.MountreachSolution.pamlabourshedulingandroidapp.Labour.LabourHomepage;

public class LoginActivity extends AppCompatActivity {
    TextView tvnewUser;
    EditText etnumber,etpassword;
    ProgressDialog progressDialog;
    AppCompatButton btnlogin;
    String mobileno ,password;
    CheckBox cbshowpassword;
    UserRegisterdatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        etnumber = findViewById(R.id.etLoginNumber);
        etpassword = findViewById(R.id.etLoginPassword);
        btnlogin = findViewById(R.id.btnLoginButton);
        cbshowpassword = findViewById(R.id.cbLoginShowpassword);
        database = new UserRegisterdatabase(LoginActivity.this);

        cbshowpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    etpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {

                    etpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

                etpassword.setSelection(etpassword.getText().length());
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etnumber.getText().toString().isEmpty()) {
                    etnumber.setError("Please enter number");
                } else if (etnumber.getText().toString().length() != 10) {
                    etnumber.setError("Please enter number properly");
                } else if (etpassword.getText().toString().isEmpty()) {
                    etpassword.setError("Please enter password");
                } else {

                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setTitle("LOG IN");
                    progressDialog.setMessage("Please wait for a while");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();


                    mobileno = etnumber.getText().toString();
                    password = etpassword.getText().toString();
                    String role = database.loginUser(mobileno, password);

                    SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (role != null) {
                        if (role.equals("INCORRECT_PASSWORD")) {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Incorrect password. Please try again.", Toast.LENGTH_SHORT).show();
                        } else if (role.equals("Farmer")) {
                            editor.putString("Role", "Farmer");
                            editor.putString("MobileNumber", mobileno);
                            editor.putBoolean("IsLoggedIn", true);
                            editor.apply();
                            progressDialog.dismiss();
                            Log.d("LoginActivity", "Mobile number saved for Farmer: " + mobileno);
                            Intent intent = new Intent(LoginActivity.this, FarmerHomepage.class);
                            startActivity(intent);
                        } else if (role.equals("Labour")) {
                            progressDialog.dismiss();
                            editor.putString("Role", "Labour");
                            editor.putString("MobileNumber", mobileno);
                            editor.putBoolean("IsLoggedIn", true);
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this, LabourHomepage.class);
                            startActivity(intent);
                        } else if (role.equals("admin")) {
                            editor.putString("Role", "admin");
                            editor.putString("MobileNumber", mobileno);
                            editor.putBoolean("IsLoggedIn", true);
                            editor.apply();
                            progressDialog.dismiss();
                            Intent intent = new Intent(LoginActivity.this, AdminHomepage.class);
                            startActivity(intent);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Unknown role: " + role, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        tvnewUser = findViewById(R.id.tvLginNewUser);
        tvnewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, selectcategory.class);
                startActivity(i);
            }
        });
    }

}