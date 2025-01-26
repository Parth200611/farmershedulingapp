package com.MountreachSolution.pamlabourshedulingandroidapp.Farmer;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.MountreachSolution.pamlabourshedulingandroidapp.UserRegisterdatabase;
import com.MountreachSolution.pamlabourshedulingandroidapp.LoginActivity;
import com.MountreachSolution.pamlabourshedulingandroidapp.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilFragment extends Fragment {
    private Button btnlogout;
    private String number;
    private CircleImageView imageView;
    private ImageButton ivedit;
    private static final int PICK_IMAGE_REQUEST = 1;
    private UserRegisterdatabase databaseHelper;
    private Bitmap selectedBitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        // Get the saved mobile number from SharedPreferences
        SharedPreferences sharedPreferences2 = getActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        number = sharedPreferences2.getString("MobileNumber", null);
        Log.d("Profil", "Mobile number saved for Farmer: " + number);

        // Initialize views
        imageView = view.findViewById(R.id.profileImage);
        ivedit = view.findViewById(R.id.editPhotoButton);
        btnlogout = view.findViewById(R.id.btnlogout);

        // Database helper
        databaseHelper = new UserRegisterdatabase(requireContext());

        // Edit profile picture button click

        // Logout button click
        btnlogout.setOnClickListener(v -> logoutUser());

        // Load saved image from database

        return view;
    }





    private void logoutUser() {
        // Clear SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Redirect to Login Activity
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        Toast.makeText(getActivity(), "Logged out successfully", Toast.LENGTH_SHORT).show();
    }
}
