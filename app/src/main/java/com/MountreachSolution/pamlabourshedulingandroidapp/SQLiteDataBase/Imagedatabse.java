package com.MountreachSolution.pamlabourshedulingandroidapp.SQLiteDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Imagedatabse extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ImageDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_IMAGES = "images";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_MOBILE_NUMBER = "mobile_number"; // Column for mobile number
    private static final String COLUMN_IMAGE_PATH = "image_path"; // Column for image path

    public Imagedatabse(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_IMAGES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MOBILE_NUMBER + " TEXT, " + // Store mobile number
                COLUMN_IMAGE_PATH + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
        onCreate(db);
    }

    // Method to insert image path into database with mobile number
    public void insertImage(String mobileNumber, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MOBILE_NUMBER, mobileNumber); // Store the mobile number
        values.put(COLUMN_IMAGE_PATH, imagePath); // Store the image path

        // Insert the values into the table
        db.insert(TABLE_IMAGES, null, values);
        db.close();
    }

    // Method to fetch image path by mobile number
    public String getImagePath(String mobileNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_IMAGES, new String[]{COLUMN_IMAGE_PATH},
                COLUMN_MOBILE_NUMBER + "=?", new String[]{mobileNumber},
                null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_PATH));
            cursor.close();
            return imagePath;
        } else {
            return null; // Return null if no record is found
        }
    }
}
