package com.example.simon.tagebuch_app.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.simon.tagebuch_app.reise.ReiseItem;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class ReisenUebersichtDatabase {
    private static final String DATABASE_NAME = "ReisenUebersicht.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "reisen";

    public static final String KEY_ID = "_id";
    public static final String KEY_ORT = "ort";
    public static final String KEY_DATE_START = "date_start";
    public static final String KEY_DATE_END = "date_end";

    public static final int COLUMN_ORT_INDEX = 1;
    public static final int COLUMN_DATE_START_INDEX = 2;
    public static final int COLUMN_DATE_END_INDEX = 3;

    private ReisenUebersichtDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public ReisenUebersichtDatabase(Context context) {
        dbHelper = new ReisenUebersichtDatabaseHelper(context, DATABASE_NAME, null,
                DATABASE_VERSION);
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

    public long insertReiseItem(ReiseItem item) {
        ContentValues itemValues = new ContentValues();
        itemValues.put(KEY_ORT, item.getOrt());
        itemValues.put(KEY_DATE_START, item.getFormattedStartDate());
        itemValues.put(KEY_DATE_END, item.getFormattedEndDate());
        return db.insert(DATABASE_TABLE, null, itemValues);
    }

    public ArrayList<ReiseItem> getAllReiseItems() {
        ArrayList<ReiseItem> items = new ArrayList<ReiseItem>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID,
                KEY_ORT, KEY_DATE_START, KEY_DATE_END }, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String ort = cursor.getString(COLUMN_ORT_INDEX);
                String startDate = cursor.getString(COLUMN_DATE_START_INDEX);
                String endDate = cursor.getString(COLUMN_DATE_END_INDEX);

                Date formattedStartDate = null;
                Date formattedEndDate = null;
                try {
                    formattedStartDate = new SimpleDateFormat("dd.MM.yyyy",
                            Locale.GERMAN).parse(startDate);
                    formattedEndDate = new SimpleDateFormat("dd.MM.yyyy",
                            Locale.GERMAN).parse(endDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                Calendar calendarStart = Calendar.getInstance(Locale.GERMAN);
                Calendar calendarEnd = Calendar.getInstance(Locale.GERMAN);

                calendarStart.setTime(formattedStartDate);
                calendarEnd.setTime(formattedEndDate);

                items.add(new ReiseItem(ort, calendarStart.get(Calendar.DAY_OF_MONTH), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.YEAR),
                        calendarEnd.get(Calendar.DAY_OF_MONTH), calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.YEAR)));

            } while (cursor.moveToNext());
        }
        return items;
    }

    public void removeReiseItem(ReiseItem item) {
        String toDelete = KEY_ORT +"=?" + " AND " + KEY_DATE_START + "=?" + " AND " + KEY_DATE_END + "=?";
        String[] deleteArguments = new String[]{String.valueOf(item.getOrt()), String.valueOf(item.getFormattedStartDate()),
                String.valueOf(item.getFormattedEndDate())};
        db.delete(DATABASE_TABLE, toDelete, deleteArguments);
        System.out.println(toDelete);
    }

    private class ReisenUebersichtDatabaseHelper extends SQLiteOpenHelper {
        private final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_ORT
                + " text not null, " + KEY_DATE_START + " text, "  + KEY_DATE_END + " text);";

        public ReisenUebersichtDatabaseHelper(Context c, String dbname,
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