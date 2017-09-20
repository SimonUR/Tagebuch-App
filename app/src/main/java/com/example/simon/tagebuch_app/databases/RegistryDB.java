package com.example.simon.tagebuch_app.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

/**
 * Created by tani on 19.09.2017.
 */


public class RegistryDB extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "RegistryDB";
    public static final String TABLE_NAME = "benutzer";

    public static final String COL_1 = "id";
    public static final String COL_2 = "name";
    public static final String COL_3 = "email";
    public static final String COL_4 = "passwort";

    public RegistryDB(Context context)
    {
        super(context,DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT,EMAIL TEXT, PASSWORT TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXIST" + TABLE_NAME);
    }

    public boolean insertData(String name, String email, String passwort)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, email);
        contentValues.put(COL_4, passwort);
        long result = db.insert(TABLE_NAME,null,contentValues);
        db.close();

        if (result == -1)
        {
            return false;
        }else {
            return true;
        }

    }

}
