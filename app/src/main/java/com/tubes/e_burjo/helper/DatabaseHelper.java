package com.tubes.e_burjo.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tubes.e_burjo.model.data;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Data.db";

    // User table name
    private static final String TABLE_BURJO = "List_Warung";

    // User Table Columns names
    private static final String COLUMN_WARUNG_ID = "ID_WARUNG";
    private static final String COLUMN_WARUNG_NAMA = "NAMA_WARUNG";
    private static final String COLUMN_WARUNG_ALAMAT = "ALAMAT_WARUNG";
    private static final String COLUMN_WARUNG_LONGITUDE = "LONGITUDE_WARUNG";
    private static final String COLUMN_WARUNG_LATITUDE = "LATITUDE_WARUNG";


    // create table sql query
    private String CREATE_WARUNG_TABLE = "CREATE TABLE " + TABLE_BURJO + "("
            + COLUMN_WARUNG_ID + " TEXT," + COLUMN_WARUNG_NAMA + " TEXT," + COLUMN_WARUNG_ALAMAT + " TEXT," + COLUMN_WARUNG_LONGITUDE + " TEXT,"
            + COLUMN_WARUNG_LATITUDE + " TEXT" + ")";
    // drop table sql query
    private String DROP_WARUNG_TABLE = "DROP TABLE IF EXISTS " + TABLE_BURJO;

    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WARUNG_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_WARUNG_TABLE);

        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param datas
     */
    public void addUser(data datas) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_WARUNG_ID, datas.getId());
        values.put(COLUMN_WARUNG_NAMA, datas.getNama());
        values.put(COLUMN_WARUNG_ALAMAT, datas.getAlamat());
        values.put(COLUMN_WARUNG_LONGITUDE, datas.getLongitude());
        values.put(COLUMN_WARUNG_LATITUDE, datas.getLatitude());

        // Inserting Row
        db.insert(TABLE_BURJO, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public ArrayList<data> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_WARUNG_ID,
                COLUMN_WARUNG_NAMA,
                COLUMN_WARUNG_ALAMAT,
                COLUMN_WARUNG_LONGITUDE,
                COLUMN_WARUNG_LATITUDE
        };
        // sorting orders
        String sortOrder =
                COLUMN_WARUNG_NAMA + " ASC";
        ArrayList<data> datalist = new ArrayList<data>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_BURJO, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                data data = new data();
                data.setId(cursor.getString(cursor.getColumnIndex(COLUMN_WARUNG_ID)));
                data.setNama(cursor.getString(cursor.getColumnIndex(COLUMN_WARUNG_NAMA)));
                data.setAlamat(cursor.getString(cursor.getColumnIndex(COLUMN_WARUNG_ALAMAT)));
                data.setLongitude(cursor.getString(cursor.getColumnIndex(COLUMN_WARUNG_LONGITUDE)));
                data.setLatitude(cursor.getString(cursor.getColumnIndex(COLUMN_WARUNG_LATITUDE)));
                // Adding user record to list
                datalist.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return datalist;
    }

    public void deleteUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_BURJO,null,null);
        db.close();
    }

}