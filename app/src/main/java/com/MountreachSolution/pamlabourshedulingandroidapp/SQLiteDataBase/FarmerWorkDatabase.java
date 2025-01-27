package com.MountreachSolution.pamlabourshedulingandroidapp.SQLiteDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class FarmerWorkDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FarmerWorkDatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_WORK_POSTING = "workposting";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FARMER_NAME = "farmername";
    private static final String COLUMN_MOBILE_NUMBER = "mobilenumber";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_WORK_IN_SHORT = "workinshort";
    private static final String COLUMN_WAGES = "wages";
    private static final String COLUMN_START_TIME = "starttime";
    private static final String COLUMN_END_TIME = "endtime";
    private static final String COLUMN_WORK_DATE = "workdate";
    private static final String COLUMN_WORK_IMAGE = "workimage";

    public FarmerWorkDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_WORK_POSTING_TABLE = "CREATE TABLE " + TABLE_WORK_POSTING + " (" +
                COLUMN_ID + " Text PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FARMER_NAME + " TEXT, " +
                COLUMN_MOBILE_NUMBER + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_WORK_IN_SHORT + " TEXT, " +
                COLUMN_WAGES + " TEXT, " +
                COLUMN_START_TIME + " TEXT, " +
                COLUMN_END_TIME + " TEXT, " +
                COLUMN_WORK_DATE + " TEXT, " +
                COLUMN_WORK_IMAGE + " TEXT" +
                ")";
        db.execSQL(CREATE_WORK_POSTING_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORK_POSTING);
        onCreate(db);
    }
    public void insertWorkPosting(String farmerName, String mobileNumber, String address,
                                  String workInShort, String wages, String startTime,
                                  String endTime, String workDate, String workImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FARMER_NAME, farmerName);
        values.put(COLUMN_MOBILE_NUMBER, mobileNumber);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_WORK_IN_SHORT, workInShort);
        values.put(COLUMN_WAGES, wages);
        values.put(COLUMN_START_TIME, startTime);
        values.put(COLUMN_END_TIME, endTime);
        values.put(COLUMN_WORK_DATE, workDate);
        values.put(COLUMN_WORK_IMAGE, workImage); // Path to the image as a string

        db.insert(TABLE_WORK_POSTING, null, values);
        db.close();
    }
    public Cursor getPaginatedWorkPostings(int limit, int offset) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_WORK_POSTING + " LIMIT ? OFFSET ?";
        return db.rawQuery(query, new String[]{String.valueOf(limit), String.valueOf(offset)});
    }
    public Cursor getdata(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+TABLE_WORK_POSTING,null);
        return cursor;
    }

}
