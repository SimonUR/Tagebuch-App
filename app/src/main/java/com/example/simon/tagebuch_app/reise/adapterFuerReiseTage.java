package com.example.simon.tagebuch_app.reise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.simon.tagebuch_app.R;

import java.util.ArrayList;

/**
 * Created by Simon on 20.09.2017.
 */

public class adapterFuerReiseTage extends ArrayAdapter<Reisetag>{
    private ArrayList<Reisetag> reiseTage;
    private Context context;

    public adapterFuerReiseTage(Context context, ArrayList<Reisetag> reiseTage){
        super(context, R.layout.listitem_reisetage, reiseTage);
        this.context = context;
        this.reiseTage = reiseTage;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listitem_reisetage, null);

        }

        Reisetag dayOfTrip = reiseTage.get(position);

        if (dayOfTrip != null) {
            TextView reiseOrt = (TextView) v.findViewById(R.id.reiseTag);
            TextView reiseBegin = (TextView) v.findViewById(R.id.reiseOrt);
            TextView reiseEnd = (TextView) v.findViewById(R.id.datum);

            reiseOrt.setText(dayOfTrip.getReiseTag());
            reiseBegin.setText(dayOfTrip.getReiseOrt());
            reiseEnd.setText(dayOfTrip.getDate());
        }

        return v;
    }

}
