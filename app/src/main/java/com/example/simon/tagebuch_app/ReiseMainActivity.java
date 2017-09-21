package com.example.simon.tagebuch_app;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.simon.tagebuch_app.databases.ReisenUebersichtDatabase;
import com.example.simon.tagebuch_app.reise.ReiseItem;
import com.example.simon.tagebuch_app.reise.ReisenAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ReiseMainActivity extends AppCompatActivity {

    private ArrayList<ReiseItem> reisen = new ArrayList<ReiseItem>();
    private ReisenAdapter reisen_adapter;
    private ReisenUebersichtDatabase reisenUebersichtDatabase;
    public static boolean startDatePicker;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reise_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Datenbank für Orte initialisieren
        initDatabase();
        // ArrayList für Reisen initialisieren
        initReiseList();
        //UI
        initUI();
        assignUserId();
        updateList();
    }

    private void assignUserId() {
        Intent intent = getIntent();
        userID = intent.getExtras().getInt("ID");
    }

    // zurück-Button in Actionbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy(){
        reisenUebersichtDatabase.close();
        super.onDestroy();
    }

    private void initDatabase() {
        reisenUebersichtDatabase = new ReisenUebersichtDatabase(this);
        reisenUebersichtDatabase.open();
    }

    private void initReiseList() {
        reisen = new ArrayList<ReiseItem>();
        initListAdapter();
    }

    private void initListAdapter() {
        ListView list = (ListView) findViewById(R.id.reise_list);
        reisen_adapter = new ReisenAdapter(this, reisen);
        list.setAdapter(reisen_adapter);
    }

    // UI
    private void initUI() {
        initTaskButton();
        initListView();
        initDateFields();
    }

    private void initDateFields() {
        final EditText startDateEdit = (EditText) findViewById(R.id.reise_begin_add);
        EditText endDateEdit = (EditText) findViewById(R.id.reise_ende_add);

        startDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startDatePicker = true;
                showEndDatePickerDialog();
            }
        });

        endDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startDatePicker = false;
                showStartDatePickerDialog();
            }
        });

    }

    private void showStartDatePickerDialog() {
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getFragmentManager(), "datePickerStart");
    }

    private void showEndDatePickerDialog() {
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getFragmentManager(), "datePickerEnd");
    }

    private void initTaskButton() {
        Button addTaskButton = (Button) findViewById(R.id.todo_edit_button);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInputToList();
                }
            });
        }
    private void addInputToList() {
        EditText ort_input = (EditText) findViewById(R.id.reise_name_add);
        EditText start_input = (EditText) findViewById(R.id.reise_begin_add);
        EditText end_input = (EditText) findViewById(R.id.reise_ende_add);

        String ort = ort_input.getText().toString();
        String start = start_input.getText().toString();
        String end = end_input.getText().toString();

            if (!ort.equals("") && !start.equals("") && !end.equals("")) {
                ort_input.setText("");
                start_input.setText("");
                end_input.setText("");
                addNewReise(ort, start, end, userID);
            }

        }
        private void addNewReise(String ort, String start, String end , int id) {

            Date startDate = getDateFromString(start);
            Date endDate = getDateFromString(end);

            GregorianCalendar calendarStart = new GregorianCalendar();
            GregorianCalendar calendarEnd = new GregorianCalendar();
            calendarStart.setTime(startDate);
            calendarEnd.setTime(endDate);

            ReiseItem newReise = new ReiseItem(ort, calendarStart.get(Calendar.DAY_OF_MONTH), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.YEAR),
                    calendarEnd.get(Calendar.DAY_OF_MONTH), calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.YEAR), id);

            reisenUebersichtDatabase.insertReiseItem(newReise);


            reisen.add(newReise);
            updateList();
            reisen_adapter.notifyDataSetChanged();

        }

    private Date getDateFromString(String date) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMANY);
        try{
            return df.parse(date);
        } catch (ParseException e){
            // return current date as fallback
            return new Date();
        }
    }

    private void updateList() {
        reisen.clear();
        reisen.addAll(reisenUebersichtDatabase.getAllReiseItemsForUser(userID));
        reisen_adapter.notifyDataSetChanged();
    }


    // #### initListView();
        private void initListView() {
            final ListView list = (ListView) findViewById(R.id.reise_list);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intentToNewActivty = new Intent(ReiseMainActivity.this, ReiseSingleTripActivity.class);

                    ReiseItem reise = (ReiseItem)list.getItemAtPosition(position);

                    String reiseName = reise.getOrt();
                    String startDate = reise.getFormattedStartDate();
                    String endDate = reise.getFormattedEndDate();

                    intentToNewActivty.putExtra("Reisename", reiseName);
                    intentToNewActivty.putExtra("Startdatum", startDate);
                    intentToNewActivty.putExtra("Enddatum", endDate);

                    startActivity(intentToNewActivty);
                }
            });
            list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    removeTaskAtPosition(position);
                    return true;
                }
            });
        }
        private void removeTaskAtPosition(int position) {
            if (reisen.get(position) != null) {
                reisenUebersichtDatabase.removeReiseItem(reisen.get(position));
                reisen.remove(position);
                updateList();
                reisen_adapter.notifyDataSetChanged();

            }
        }


}