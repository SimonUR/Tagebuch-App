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

    private static final String KEY_ID = "_id";
    private static final String KEY_TAG = "tag";
    private static final String KEY_DATE = "date";
    private static final String KEY_USER_ID = "user_id";

    private static final int COLUMN_TAG_INDEX = 1;
    private static final int COLUMN_DATE_INDEX = 2;
    private static final int COLUMN_USER_ID_INDEX = 3;

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
        itemValues.put(KEY_DATE, reisetag.getDate());
        itemValues.put(KEY_USER_ID, reisetag.getUserID());
        return db.insert(DATABASE_TABLE, null, itemValues);
    }

    public ArrayList<Reisetag> getAllReiseDaysForUser(int userId) {
        ArrayList<Reisetag> items = new ArrayList<Reisetag>();
        String query = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + KEY_USER_ID + " = " + userId;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String tag = cursor.getString(COLUMN_TAG_INDEX);
                String date = cursor.getString(COLUMN_DATE_INDEX);
                int id = cursor.getInt(COLUMN_USER_ID_INDEX);

                items.add(new Reisetag(tag, date, id));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    private class reiseTagedbHelper extends SQLiteOpenHelper{
        private final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_TAG
                + " text not null, "  + KEY_DATE + " text, " + KEY_USER_ID + " integer);";

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
