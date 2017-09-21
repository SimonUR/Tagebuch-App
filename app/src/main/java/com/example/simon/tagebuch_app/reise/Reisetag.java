package com.example.simon.tagebuch_app.reise;

/**
 * Created by Simon on 20.09.2017.
 */

public class Reisetag {

    private String reiseTag;
    private String date;
    private int userID;

    public String getReiseTag() {
        return reiseTag;
    }

    public String getDate() {
        return date;
    }

    public int getUserID(){
        return userID;
    }

    public Reisetag(String reiseTag, String date, int userID){
        this.reiseTag = reiseTag;
        this.date = date;
        this.userID = userID;

    }

}
