package com.example.simon.tagebuch_app.reise;


public class ReiseItem {
    private String ort;
    private String start;
    private String end;

    public ReiseItem(String ort, String start, String end) {
        this.ort = ort;
        this.start = start;
        this.end = end;
    }


    public String getOrt() {
        return ort;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }


    @Override
    public String toString() {
        return "Ort: " + getOrt() + ", Beginn: " + getStart() + ", Ende: " + getEnd();
    }
}