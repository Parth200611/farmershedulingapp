package com.MountreachSolution.pamlabourshedulingandroidapp.Farmer;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.MountreachSolution.pamlabourshedulingandroidapp.SQLiteDataBase.Imagedatabse;
import com.MountreachSolution.pamlabourshedulingandroidapp.SQLiteDataBase.UserRegisterdatabase;
import com.MountreachSolution.pamlabourshedulingandroidapp.LoginActivity;
import com.MountreachSolution.pamlabourshedulingandroidapp.R;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilFragment extends Fragment {
    private Button btnlogout;
    private String mobileNumber;
    private CircleImageView imageView;
    private ImageButton ivedit;
    private static final int PICK_IMAGE = 100;
    private Imagedatabse dbHelper;
    private UserRegisterdatabase databaseHelper;
    private Bitmap selectedBitmap;
    TextView tvname,tvage,tvadhareno,tvaddress,tvMobileNo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        // Get the saved mobile number from SharedPreferences
        SharedPreferences sharedPreferences2 = getActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        mobileNumber = sharedPreferences2.getString("MobileNumber", null);
        Log.d("Profil", "Mobile number saved for Farmer: " + mobileNumber);

        // Initialize views
        imageView = view.findViewById(R.id.profileImage);
        ivedit = view.findViewById(R.id.editPhotoButton);
        btnlogout = view.findViewById(R.id.btnlogout);
        tvname=view.findViewById(R.id.tvnameValue);
        tvage=view.findViewById(R.id.agevalue);
        tvadhareno=view.findViewById(R.id.adharvalue);
        tvaddress=view.findViewById(R.id.addressValue);
        tvMobileNo=view.findViewById(R.id.Mobilenovalue);

        dbHelper = new Imagedatabse(getActivity());
        databaseHelper = new UserRegisterdatabase(requireContext());

        loadProfileImage();
        fetchUserData();

        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        ivedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        // Logout button click
        btnlogout.setOnClickListener(v -> logoutUser());

        return view;
    }

    private void fetchUserData() {
        Cursor cursor = databaseHelper.getUserDataByMobile(mobileNumber);

        if (cursor != null && cursor.moveToFirst()) {

            String name = cursor.getString(cursor.getColumnIndex("name"));
            String mobile = cursor.getString(cursor.getColumnIndex("mobileno"));
            String age = cursor.getString(cursor.getColumnIndex("age"));
            String address = cursor.getString(cursor.getColumnIndex("address"));
            String adhar = cursor.getString(cursor.getColumnIndex("adharno"));



            tvname.setText(name);
            tvMobileNo.setText(mobile);
            tvage.setText(age);
            tvaddress.setText(address);
            tvadhareno.setText(adhar);


            cursor.close();
        } else {
            Toast.makeText(getActivity(), "No user data found", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadProfileImage() {
        String imagePath = dbHelper.getImagePath(mobileNumber);

        if (imagePath != null) {
            // Use Glide to load the image into the CircleImageView
            Glide.with(getActivity())
                    .load(imagePath)
                    .error(R.drawable.baseline_person_24)  // Set a default image if loading fails
                    .into(imageView);
        } else {
            // If no image path exists, you can set a default or placeholder image
            imageView.setImageResource(R.drawable.baseline_person_24); // Put a default image in resources
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            String imagePath = getRealPathFromURI(selectedImageUri);

            // Insert image path into the database with mobile number
            dbHelper.insertImage(mobileNumber, imagePath);

            // You can fetch the image path later like this:
            String fetchedImagePath = dbHelper.getImagePath(mobileNumber);
            Log.d("Image Path", fetchedImagePath);
        } else {
            Log.d("Error", "Data is null or image picking was canceled!");
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // For Android 10 and above, use MediaStore API to handle URI
            return contentUri.toString();
        } else {
            // For earlier versions, use the traditional method to get the real file path
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                String path = cursor.getString(columnIndex);
                cursor.close();
                return path;
            }
            return null;
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) { // Check for READ_EXTERNAL_STORAGE permission request
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can load the image now
                Log.d("Permission", "Permission granted!");
            } else {
                // Permission denied, inform the user
                Toast.makeText(getActivity(), "Permission denied. Can't access the image.", Toast.LENGTH_LONG).show();
            }
        }
    }
}