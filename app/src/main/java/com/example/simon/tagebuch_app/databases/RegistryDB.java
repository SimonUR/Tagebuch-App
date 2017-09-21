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

    public static final String COL_1 = "id";
    public static final String COL_2 = "name";
    public static final String COL_3 = "email";
    public static final String COL_4 = "passwort";

    public static final int COL_id = 0;
    public static final int COL_name = 1;
    public static final int COL_email = 2;
    public static final int COL_password = 3;



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

    public boolean checkLogInInfo(String password){
        String checkerSQLSTATEMENT = "SELECT " + COL_2 + " FROM " + TABLE_NAME + " WHERE " + COL_4 + " = '"
                + password + "'";
        Cursor cursor = db.rawQuery(checkerSQLSTATEMENT, null);
        cursor.moveToFirst();
        System.out.println(cursor.getString(0));

        return false;
    }

    public boolean insertData(String name, String email, String passwort)
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

        public registrydbHelper(Context c, String dbname,
                                SQLiteDatabase.CursorFactory factory, int version) {
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

