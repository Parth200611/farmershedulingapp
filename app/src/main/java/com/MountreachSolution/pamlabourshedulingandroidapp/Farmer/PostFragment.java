package com.MountreachSolution.pamlabourshedulingandroidapp.Farmer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.MountreachSolution.pamlabourshedulingandroidapp.R;
import com.MountreachSolution.pamlabourshedulingandroidapp.SQLiteDataBase.FarmerWorkDatabase;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class PostFragment extends Fragment {
    private static final int IMAGE_PICK_REQUEST =100 ;
    EditText etwork,etlabourrequired,etaddress,etwages,etsatrttime,etendtime,etworkingdate;
    Button btnaddimage;
    ImageView ivwork;
    AppCompatButton btnPOst;
    String strname,strmobileno,strwork,strlabour,straddress,strwages,strstartimae,strendtime,strdate;
    private Bitmap selectedImageBitmap = null;
    private FarmerWorkDatabase farmerWorkDatabase;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_post, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Userdata", Context.MODE_PRIVATE);
        // Fetch data using the keys
         strname = sharedPreferences.getString("userName", "Default Name");
         strmobileno = sharedPreferences.getString("userMobile", "Default Mobile");
        Log.d("UserData", "Fetched Name: " + strname);
        Log.d("UserData", "Fetched Mobile: " + strmobileno);

         etwork=view.findViewById(R.id.etwork);
         etlabourrequired=view.findViewById(R.id.etrequiredlabour);
         etaddress=view.findViewById(R.id.etworkaddress);
         etwages=view.findViewById(R.id.etwages);
         etsatrttime=view.findViewById(R.id.etstarrttime);
         etendtime=view.findViewById(R.id.etendtime);
         etworkingdate=view.findViewById(R.id.etworkingdate);
         ivwork=view.findViewById(R.id.ivworkimage);
         btnaddimage=view.findViewById(R.id.btnaddworkimage);
         btnPOst=view.findViewById(R.id.btnPostwork);
         
         farmerWorkDatabase= new FarmerWorkDatabase(getContext());

        btnaddimage.setOnClickListener(v -> openGallery());

         btnPOst.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if (etaddress.getText().toString().isEmpty()){
                     etaddress.setError("Enter Address");
                 }else if (etwork.getText().toString().isEmpty()){
                     etwork.setError("Enter Address");
                 }else if (etlabourrequired.getText().toString().isEmpty()){
                     etlabourrequired.setError("Enter Address");
                 }else if (etwages.getText().toString().isEmpty()){
                     etwages.setError("Enter Address");
                 }else if (etsatrttime.getText().toString().isEmpty()){
                     etsatrttime.setError("Enter Address");
                 }else if (etworkingdate.getText().toString().isEmpty()){
                     etworkingdate.setError("Enter Address");
                 }else {
                     strwork=etwork.getText().toString();
                     strlabour=etlabourrequired.getText().toString();
                     straddress=etaddress.getText().toString();
                     strwages=etwages.getText().toString();
                     strstartimae=etsatrttime.getText().toString();
                     strendtime=etendtime.getText().toString();
                     strdate=etworkingdate.getText().toString();
                     postWork();

                 }
             }
         });

        return view;
    }

    private void postWork() {
        // Get input data


        // Convert image to Base64 string
        String workImageBase64 = encodeImageToBase64(selectedImageBitmap);

        // Insert into database
        SQLiteDatabase db = farmerWorkDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("farmername", strname);
        values.put("mobilenumber", strmobileno);
        values.put("address", straddress);
        values.put("workinshort", strwork);
        values.put("wages", strwages);
        values.put("starttime", strstartimae);
        values.put("endtime", strendtime);
        values.put("workdate", strdate);
        values.put("workimage", workImageBase64);

        long result = db.insert("workposting", null, values);
        db.close();

        if (result != -1) {
            Toast.makeText(getContext(), "Work posted successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(getContext(), "Failed to post work", Toast.LENGTH_SHORT).show();
        }
    }

    private String encodeImageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void clearFields() {

        etaddress.setText("");
        etlabourrequired.setText("");
        etwork.setText("");
        etwages.setText("");
        etsatrttime.setText("");
        etendtime.setText("");
        etworkingdate.setText("");
        ivwork.setImageResource(R.drawable.baseline_camera_alt_24); // Set a placeholder image
        selectedImageBitmap = null;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_PICK_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(imageUri);
                selectedImageBitmap = BitmapFactory.decodeStream(inputStream);
                ivwork.setImageBitmap(selectedImageBitmap); // Show selected image in ImageView
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}