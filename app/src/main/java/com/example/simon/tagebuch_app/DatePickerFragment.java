package com.example.simon.tagebuch_app;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Jonas on 22.05.2017.
 */

public class DatePickerFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        if(ReiseMainActivity.startDatePicker) {
            TextView startView = (TextView) getActivity().findViewById(R.id.reise_begin_add);

            GregorianCalendar startDate = new GregorianCalendar(year, month, day);
            DateFormat startFirmattedDate = DateFormat.getDateInstance(DateFormat.SHORT,
                    Locale.GERMANY);
            String startDateString = startFirmattedDate.format(startDate.getTime());

            startView.setText(startDateString);
        }else {

            TextView endView = (TextView) getActivity().findViewById(R.id.reise_ende_add);

            GregorianCalendar endDate = new GregorianCalendar(year, month, day);
            DateFormat endFormattedDate = DateFormat.getDateInstance(DateFormat.SHORT,
                    Locale.GERMANY);
            String endDateString = endFormattedDate.format(endDate.getTime());

            endView.setText(endDateString);
        }
    }
}