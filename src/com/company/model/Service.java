package com.company.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Service {
    private Calendar ser_date;
    private String city;
    private float cost;
    private float priv_coast;

    public Service(Calendar ser_date, String city, float cost, float priv_coast) {
        this.city = city;
        this.cost = cost;
        this.priv_coast = priv_coast;
        this.ser_date = fixDate(ser_date);
    }

    public Service(String city) {
        this.city = city;
    }

    public Calendar getSerDate() {
        return ser_date;
    }

    public String getCity() {
        return city;
    }

    public float getCost() {
        return cost;
    }

    public float getPrivCoast() {
        return priv_coast;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public void setPrivCoast(float priv_coast) {
        this.priv_coast = priv_coast;
    }

    public void setSerDate(Calendar ser_date) {
        this.ser_date = ser_date;
    }

    private Calendar fixDate(Calendar calendar) {
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        return calendar;
    }
}
