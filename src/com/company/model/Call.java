package com.company.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Call {
    private  int callId;
    private Calendar call_date;
    private String city;
    private String number;
    private int duration;

    public Call(int callId, Calendar call_date, String city, String number, int duration) {
        this.callId = callId;
        this.call_date = call_date;
        this.duration = duration;
        this.city = city;
        this.number = number;
    }

    public Call(Calendar call_date, String city, String number, int duration) {
        this.call_date = call_date;
        this.duration = duration;
        this.city = city;
        this.number = number;
    }

    public Call(int callId) {
        this.callId = callId;
    }

    public int getCallId() {
        return callId;
    }

    public Calendar getCallDate() {
        return call_date;
    }

    public String getNumber() {
        return number;
    }

    public int getDuration() {
        return duration;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCallDate(Calendar call_date) {
        this.call_date = call_date;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String dateToString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        return dateFormat.format(call_date.getTime());
    }
}
