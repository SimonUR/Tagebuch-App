package com.example.simon.tagebuch_app.reise;

/**
 * Created by Simon on 20.09.2017.
 */

public class Reisetag {

    private String reiseTag;
    private String reiseOrt;
    private String date;

    public String getReiseTag() {
        return reiseTag;
    }

    public String getReiseOrt() {
        return reiseOrt;
    }

    public String getDate() {
        return date;
    }

    public Reisetag(String reiseTag, String reiseOrt, String date){
        this.reiseTag = reiseTag;
        this.reiseOrt = reiseOrt;
        this.date = date;

    }

}
