package com.example.simon.tagebuch_app;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simon.tagebuch_app.databases.RegistryDB;
import com.example.simon.tagebuch_app.reise.GPSActivity;
import com.example.simon.tagebuch_app.reise.GPSTracker;

public class ReiseSingleDayActivity extends AppCompatActivity {

    private GPSTracker gps;
    private Button locationButton;
    private Button addTextButton;
    private AlertDialog alertDialog;
    private Button cancelButton;
    private Button addTextToDb;
    private RegistryDB newDB;

    private String day;
    private String date;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reise_single_day);
        initUI();
        initDB();
        assignIntentInfos();
    }

    @Override
    protected void onDestroy(){
        newDB.close();
        super.onDestroy();
    }

    private void initDB() {
        newDB = new RegistryDB(this);
        newDB.open();
    }

    private void assignIntentInfos() {
        Intent intent = getIntent();
        Bundle infos = intent.getExtras();
        day = infos.getString("Day");
        date = infos.getString("Date");
        userId = infos.getInt("userId");

    }

    private void initUI() {
        locationButton = (Button) findViewById(R.id.assignLocationButton);
        addTextButton = (Button) findViewById(R.id.userAddText);
        textButtonAddListener();
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create class object
                gps = new GPSTracker(ReiseSingleDayActivity.this);

                // check if GPS enabled
                if(gps.canGetLocation()){

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is: \nLat: "
                            + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

            }
        });
    }

    private void textButtonAddListener() {
        addTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
    }

    private void showAlertDialog() {
        alertDialog = new AlertDialog.Builder(ReiseSingleDayActivity.this).create();
        LayoutInflater inflater = ReiseSingleDayActivity.this.getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_layout, null);
        alertDialog.setView(layout);
        alertDialog.show();
        setCancelButtton();
        setAddTextButton();
    }

    private void setAddTextButton() {
        addTextToDb = (Button) alertDialog.findViewById(R.id.addTextToDb);
        addTextToDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private void setCancelButtton() {
        cancelButton = (Button) alertDialog.findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}
