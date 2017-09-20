package com.example.simon.tagebuch_app;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.simon.tagebuch_app.databases.reiseTageDatabase;
import com.example.simon.tagebuch_app.reise.Reisetag;
import com.example.simon.tagebuch_app.reise.adapterFuerReiseTage;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ReiseSingleTripActivity extends AppCompatActivity {

    private ArrayList<Reisetag> reiseTage = new ArrayList<Reisetag>();
    private adapterFuerReiseTage reisetage_adapter;
    private reiseTageDatabase reiseTagedb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reise_single_trip);
            initDatabase();
            initReiseTageList();
            addDaysToList();


    }

    @Override
    protected void onDestroy(){
        reiseTagedb.close();
        super.onDestroy();
    }


    private void addDaysToList() {
        Intent intent = getIntent();
        Bundle tripInfo = intent.getExtras();
        String tripName = tripInfo.get("Reisename").toString();
        String startDate = tripInfo.get("Startdatum").toString();
        String endDate = tripInfo.get("Enddatum").toString();

        Date formattedStartDate = null;
        Date formattedEndDate = null;


        try{
            formattedStartDate = new SimpleDateFormat("dd.MM.yyyy").parse(startDate);
            formattedEndDate = new SimpleDateFormat("dd.MM.yyyy").parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal2 = Calendar.getInstance(Locale.GERMAN);
        Calendar cal1 = Calendar.getInstance(Locale.GERMAN);

        cal1.setTime(formattedEndDate);
        cal2.setTime(formattedStartDate);

        System.out.println("Days= " + daysBetween(cal2.getTime(), cal1.getTime()));

        startDate = cal2.get(Calendar.DAY_OF_MONTH) + "." + cal2.get(Calendar.MONTH) + "." + cal2.get(Calendar.YEAR);
        if (!tripName.equals("") && !startDate.equals("") && !endDate.equals("")) {
            addNewTrip(tripName, startDate, endDate);
        }
    }

    public int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    private void addNewTrip(String tripName, String startDate, String endDate) {
        Reisetag newTripDay = new Reisetag(tripName, startDate, endDate);
        reiseTagedb.insertReiseDay(newTripDay);
        reiseTage.add(newTripDay);
        updateList();
        reisetage_adapter.notifyDataSetChanged();
    }

    private void updateList() {
        reiseTage.clear();
        reiseTage.addAll(reiseTagedb.getAllReiseDays());
        reisetage_adapter.notifyDataSetChanged();
    }

    private void initListView() {
        ListView list = (ListView) findViewById(R.id.reisetage_list);
        reisetage_adapter = new adapterFuerReiseTage(this, reiseTage);
        list.setAdapter(reisetage_adapter);
    }

    private void initReiseTageList() {
        reiseTage = new ArrayList<Reisetag>();
        initListView();
    }

    private void initDatabase() {
        reiseTagedb = new reiseTageDatabase(this);
        reiseTagedb.open();
    }

}
