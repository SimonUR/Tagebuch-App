package com.example.simon.tagebuch_app.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

import static com.example.simon.tagebuch_app.databases.RegistryDB.COL_1;
import static com.example.simon.tagebuch_app.databases.RegistryDB.COL_2;
import static com.example.simon.tagebuch_app.databases.RegistryDB.COL_3;
import static com.example.simon.tagebuch_app.databases.RegistryDB.COL_4;
import static com.example.simon.tagebuch_app.databases.RegistryDB.TABLE_NAME;

/**
 * Created by tani on 19.09.2017.
 */


public class RegistryDB {
    public static final String DATABASE_NAME = "Registry.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "benutzer";
    private static final String SINGLE_DAY_TABLE_NAME = "singleday";

    public static final String COL_1 = "id";
    public static final String COL_2 = "name";
    public static final String COL_3 = "email";
    public static final String COL_4 = "passwort";

    private static final String SINGLE_DAY_DB_KEY_ID = "id";
    private static final String SINGLE_DAY_DB_KEY_TEXT = "usertext";
    private static final String SINGLE_DAY_DB_KEY_IMAGE = "image";
    private static final String SINGLE_DAY_DB_KEY_LOCATION_LAT = "latitude";
    private static final String SINGLE_DAY_DB_KEY_LOCATION_LONG = "longitude";


    private static final int COL_INDEX_0 = 0;
    private static final int COL_INDEX_1 = 1;
    private static final int COL_INDEX_2 = 2;
    private static final int COL_INDEX_3 = 3;
    private static final int COL_INDEX_4 = 4;



    private registrydbHelper dbHelper;
    private SQLiteDatabase db;

    public RegistryDB(Context context){
        dbHelper = new registrydbHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() throws SQLException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
    }

    public void close() {
        db.close();
    }

    public int getUserID(String email, String password){
        String query = "SELECT "+ COL_1 + " FROM " + TABLE_NAME + " WHERE " + COL_4 + " = '"
                + password + "' AND " + COL_3 + " = '" + email + "'";
        int id;
            Cursor cursor = db.rawQuery(query, null);
                cursor.moveToFirst();
                id = cursor.getInt(0);
        cursor.close();
        return id;
    }

    public boolean checkLogInInfo(String email, String password){
        String query = "SELECT * " + " FROM " + TABLE_NAME + " WHERE " + COL_4 + " = '"
                + password + "' AND " + COL_3 + " = '" + email + "'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() <= 0){
            cursor.close();

            return false;
        }
        cursor.close();
        return true;
    }

    public boolean insertUserInDb(String name, String email, String passwort)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, email);
        contentValues.put(COL_4, passwort);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
        }



    private class registrydbHelper extends SQLiteOpenHelper {

        private final String DATABASE_CREATE = "create table "
                + TABLE_NAME + " (" + COL_1
                + " integer primary key autoincrement, " + COL_2
                + " text not null, " + COL_3 + " text, "  + COL_4 + " text);";

        private final String DATABASE_SINGLE_DAY_CREATE = "create table "
                + SINGLE_DAY_TABLE_NAME + " ("
                + SINGLE_DAY_DB_KEY_ID + " integer primary key autoincrement, "
                + SINGLE_DAY_DB_KEY_TEXT + " text, "
                + SINGLE_DAY_DB_KEY_IMAGE + " BLOB, "
                + SINGLE_DAY_DB_KEY_LOCATION_LAT + "double, "
                + SINGLE_DAY_DB_KEY_LOCATION_LONG + "double);";


        public registrydbHelper(Context c, String dbname,
                                SQLiteDatabase.CursorFactory factory, int version) {
            super(c, dbname, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_SINGLE_DAY_CREATE);
            db.execSQL(DATABASE_CREATE);
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}