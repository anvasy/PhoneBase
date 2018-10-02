package com.company.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Subscriber {
    private String number;
    private String fio;
    private String address;
    private Calendar reg_date;

    public Subscriber(String number, String fio, String address, Calendar reg_date) {
        this.number = number;
        this.fio = fio;
        this.address = address;
        this.reg_date = fixDate(reg_date);
    }

    public Subscriber(String number) {
        this.number = number;
    }

    public Calendar getRegDate() {
        return reg_date;
    }

    public String getAddress() {
        return address;
    }

    public String getFio() {
        return fio;
    }

    public String getNumber() {
        return number;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setRegDate(Calendar reg_date) {
        this.reg_date = reg_date;
    }

    public String dateToString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        return dateFormat.format(reg_date.getTime());
    }

    private Calendar fixDate(Calendar calendar) {
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        return calendar;
    }
}
