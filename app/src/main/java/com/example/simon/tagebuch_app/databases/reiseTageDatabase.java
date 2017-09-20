package com.example.simon.tagebuch_app.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.simon.tagebuch_app.reise.Reisetag;


import java.util.ArrayList;


/**
 * Created by Simon on 20.09.2017.
 */

public class reiseTageDatabase {
    private static final String DATABASE_NAME = "reiseTageUebersicht.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "reiseTage";

    public static final String KEY_ID = "_id";
    public static final String KEY_TAG = "tag";
    public static final String KEY_ORT = "ort";
    public static final String KEY_DATE = "date";

    public static final int COLUMN_TAG_INDEX = 1;
    public static final int COLUMN_ORT_INDEX = 2;
    public static final int COLUMN_DATE_INDEX = 3;

    private reiseTagedbHelper dbHelper;
    private SQLiteDatabase db;

    public reiseTageDatabase(Context context) {
        dbHelper = new reiseTagedbHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() throws SQLException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
    }

    public void close(){
        db.close();
    }

    public long insertReiseDay(Reisetag reisetag){
        ContentValues itemValues = new ContentValues();
        itemValues.put(KEY_TAG, reisetag.getReiseTag());
        itemValues.put(KEY_ORT, reisetag.getReiseOrt());
        itemValues.put(KEY_DATE, reisetag.getDate());
        return db.insert(DATABASE_TABLE, null, itemValues);
    }

    public ArrayList<Reisetag> getAllReiseDays() {
        ArrayList<Reisetag> items = new ArrayList<Reisetag>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID ,KEY_TAG,
                KEY_ORT, KEY_DATE}, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String tag = cursor.getString(COLUMN_TAG_INDEX);
                String ort = cursor.getString(COLUMN_ORT_INDEX);
                String date = cursor.getString(COLUMN_DATE_INDEX);


                items.add(new Reisetag(tag, ort, date));

            } while (cursor.moveToNext());
        }
        return items;
    }

    private class reiseTagedbHelper extends SQLiteOpenHelper{
        private final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_TAG
                + " text not null, " + KEY_ORT + " text, "  + KEY_DATE + " text);";

        public reiseTagedbHelper(Context c, String dbname, SQLiteDatabase.CursorFactory factory, int version) {
            super(c, dbname, factory, version);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
