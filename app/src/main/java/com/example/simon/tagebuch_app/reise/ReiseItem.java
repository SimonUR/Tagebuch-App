package com.example.simon.tagebuch_app.reise;


import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ReiseItem {
    private String ort;
    private GregorianCalendar calendarStart;
    private GregorianCalendar calendarEnd;
    private int userID;

    public ReiseItem(String ort, int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear, int userID) {
        this.ort = ort;
        calendarStart = new GregorianCalendar(startYear, startMonth, startDay);
        calendarEnd = new GregorianCalendar(endYear, endMonth, endDay);
        this.userID = userID;
    }

    public String getFormattedStartDate(){
        DateFormat date = DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMANY);
        return date.format(calendarStart.getTime());
    }

    public String getFormattedEndDate(){
        DateFormat date = DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMANY);
        return date.format(calendarEnd.getTime());
    }

    public String getOrt() {
        return ort;
    }

    public Date getStartDate() {
        return calendarStart.getTime();
    }

    public Date getEndDate(){
        return calendarEnd.getTime();
    }

    public int getUserID(){
        return userID;
    }


    @Override
    public String toString() {
        return "Ort: " + getOrt() + ", Beginn: " + getFormattedStartDate() + ", Ende: " + getFormattedEndDate();
    }
}