package com.example.simon.tagebuch_app;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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
    private int userID;
    private int lengthOfTrip = 1;
    private String tripName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reise_single_trip);
            initDatabase();
            assignLengthOfTrip();
            initReiseTageList();
            assignUserId();
            updateList();
        setTitle(tripName);


    }

    private void assignLengthOfTrip() {
        Intent intent = getIntent();
        Bundle tripInfo = intent.getExtras();
        String startDate = tripInfo.get("Startdatum").toString();
        String endDate = tripInfo.get("Enddatum").toString();
        tripName = tripInfo.get("Reisename").toString();

        Date formattedStartDate = null;
        Date formattedEndDate = null;


        try{
            formattedStartDate = new SimpleDateFormat("dd.MM.yyyy").parse(startDate);
            formattedEndDate = new SimpleDateFormat("dd.MM.yyyy").parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calStart = Calendar.getInstance(Locale.GERMAN);
        Calendar calEnd = Calendar.getInstance(Locale.GERMAN);

        calEnd.setTime(formattedEndDate);
        calStart.setTime(formattedStartDate);

        lengthOfTrip += daysBetween(calStart.getTime(), calEnd.getTime());
    }

    private void assignUserId() {
        Intent intent = getIntent();
        userID = intent.getExtras().getInt("Id");
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


        if (!tripName.equals("") && !startDate.equals("") && !endDate.equals("")) {

            for(int i = 0; i < lengthOfTrip; i++) {
                Date date = null;
                try {
                    date = new SimpleDateFormat("dd.MM.yyyy").parse(startDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DATE, i );
                int dayNumber = i + 1;
                String numberOfDay = "Tag " + dayNumber;
                String dateOfDay = c.get(Calendar.DAY_OF_MONTH) + "." + c.get(Calendar.MONTH) + "." + c.get(Calendar.YEAR);


                addNewTrip(numberOfDay, dateOfDay);
            }
        }
    }

    public int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    private void addNewTrip(String day, String date) {
        Reisetag newTripDay = new Reisetag(day, date, userID);
        reiseTagedb.insertReiseDay(newTripDay);
        reiseTage.add(newTripDay);
        reisetage_adapter.notifyDataSetChanged();
    }

    private void updateList() {
        reiseTage.clear();
        addDaysToList();
        reisetage_adapter.notifyDataSetChanged();
    }

    private void initListView() {
        final ListView list = (ListView) findViewById(R.id.reisetage_list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent getToDayActivity = new Intent(ReiseSingleTripActivity.this, ReiseSingleDayActivity.class);
                Reisetag selectedDay = (Reisetag)list.getItemAtPosition(position);
                String day = selectedDay.getReiseTag();
                String date = selectedDay.getDate();
                getToDayActivity.putExtra("Day", day);
                getToDayActivity.putExtra("Date", date);
                getToDayActivity.putExtra("UserId", userID);
                startActivity(getToDayActivity);
            }
        });
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
