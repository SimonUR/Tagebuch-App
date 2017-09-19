package com.example.simon.tagebuch_app;

import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.simon.tagebuch_app.databases.ReisenUebersichtDatabase;
import com.example.simon.tagebuch_app.reise.Reise;
import com.example.simon.tagebuch_app.reise.ReiseItem;
import com.example.simon.tagebuch_app.reise.ReisenAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ReiseMainActivity extends AppCompatActivity {

    private ArrayList<ReiseItem> reisen;
    private ReisenAdapter reisen_adapter;
    private ReisenUebersichtDatabase reisenUebersichtDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reise_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // Datenbank für Orte initialisieren
        initDatabase();
        // ArrayList für Reisen initialisieren
        initReiseList();
        //UI
        initUI();
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
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
                addNewReise(ort, start, end);
            }

        }
        private void addNewReise(String ort, String start, String end) {

            ReiseItem newReise = new ReiseItem(ort, start, end);

            reisenUebersichtDatabase.insertReiseItem(newReise);


            reisen.add(newReise);
            updateList();
            reisen_adapter.notifyDataSetChanged();
            System.out.println("AHHHHHHHHHHAAAAAAAAAAAAAAAA!!!!!!!!!!!!!");

        }

    private void updateList() {
        reisen.clear();
        reisen.addAll(reisenUebersichtDatabase.getAllReiseItems());
        reisen_adapter.notifyDataSetChanged();
    }


    // #### initListView();
        private void initListView() {
            ListView list = (ListView) findViewById(R.id.reise_list);
            list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view,
                                               int position, long id) {
                    removeTaskAtPosition(position);
                    return true;
                }
            });
        }
        private void removeTaskAtPosition(int position) {
            if (reisen.get(position) != null) {
                reisenUebersichtDatabase.removeReiseItem(reisen.get(position));

            }
        }

}