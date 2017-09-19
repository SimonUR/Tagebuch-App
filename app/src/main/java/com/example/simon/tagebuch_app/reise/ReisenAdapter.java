package com.example.simon.tagebuch_app.reise;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.simon.tagebuch_app.R;

import java.util.ArrayList;


public class ReisenAdapter extends ArrayAdapter<ReiseItem> {
    private ArrayList<ReiseItem> reiseItems;
    private Context context;

    public ReisenAdapter(Context context, ArrayList<ReiseItem> reiseItems) {
        super(context, R.layout.listitem_reiselist, reiseItems);
        this.context = context;
        this.reiseItems = reiseItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listitem_reiselist, null);

        }

        ReiseItem reiseItem = reiseItems.get(position);

        if (reiseItem != null) {
            TextView reiseOrt = (TextView) v.findViewById(R.id.reise_name);
            TextView reiseBegin = (TextView) v.findViewById(R.id.reise_begin);
            TextView reiseEnd = (TextView) v.findViewById(R.id.reise_ende);

            reiseOrt.setText(reiseItem.getOrt());
            reiseBegin.setText(reiseItem.getStart());
            reiseEnd.setText(reiseItem.getEnd());
        }

        return v;
    }
}
