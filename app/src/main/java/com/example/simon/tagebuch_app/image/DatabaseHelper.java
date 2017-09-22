package com.example.simon.tagebuch_app.image;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase database;

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "database_name";

    // Table Names
    private static final String DB_TABLE = "table_image";

    // column names
    private static final String KEY_NAME = "image_name";
    private static final String KEY_IMAGE = "image_data";

    // Table create statement
    private static final String CREATE_TABLE_IMAGE = "CREATE TABLE " + DB_TABLE + "("+
            KEY_NAME + " TEXT," +
            KEY_IMAGE + " BLOB);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        openDB();
    }

    public void openDB() {
        database = this.getWritableDatabase();
    }

    public void addImage( String name, byte[] image) throws SQLiteException {
        openDB();
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);
        cv.put(KEY_IMAGE, image);
        database.insert(DB_TABLE, null, cv );
    }

    public ArrayList<Bitmap> getAllImageFromDB() {
        ArrayList<Bitmap> images = new ArrayList<Bitmap>();
        String SQL = "SELECT " + KEY_IMAGE + " FROM " + DB_TABLE;
        Cursor cursor = database.rawQuery(SQL, null);
        if (cursor.moveToFirst()) {
            do {
                byte[] image = cursor.getBlob(0);
                images.add(getImage(image));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return images;

    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating table
        db.execSQL(CREATE_TABLE_IMAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);

        // create new table
        onCreate(db);
    }
}