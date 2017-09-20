package com.example.simon.tagebuch_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.simon.tagebuch_app.databases.reiseTageDatabase;
import com.example.simon.tagebuch_app.reise.Reisetag;
import com.example.simon.tagebuch_app.reise.adapterFuerReiseTage;

import java.util.ArrayList;

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
        Log.v("NAEM", tripName);
        Log.v("DATE", startDate);

        if (!tripName.equals("") && !startDate.equals("") && !endDate.equals("")) {
            addNewTrip(tripName, startDate, endDate);
        }
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
