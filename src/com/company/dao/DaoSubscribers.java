package com.company.dao;

import com.company.db.DataBase;
import com.company.model.Subscriber;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DaoSubscribers implements Dao<Subscriber> {

    private DataBase db;

    public DaoSubscribers(DataBase db) {
        this.db = db;
    }

    @Override
    public void insert(Subscriber subscriber) throws SQLException {
        PreparedStatement ps = (PreparedStatement) db.getCn().prepareStatement(
                "insert into subscribers (number,fio,address,reg_date)"
                        + "values(?,?,?,?)");

        ps.setString(1, subscriber.getNumber());
        ps.setString(2, subscriber.getFio());
        ps.setString(3, subscriber.getAddress());
        ps.setDate(4, new Date(subscriber.getRegDate().YEAR, subscriber.getRegDate().MONTH, subscriber.getRegDate().DAY_OF_MONTH));
        ps.execute();
    }

    @Override
    public void delete(Subscriber subscriber) throws SQLException {
        System.out.println(subscriber.getNumber());
        db.update("delete from subscribers where number = '" + subscriber.getNumber() +"'");
    }

    @Override
    public void update(Subscriber subscriber) throws SQLException {
        PreparedStatement ps = (PreparedStatement) db.getCn().prepareStatement(
                "update subscribers set fio=?, address=?, reg_date=? where number='"
                        + subscriber.getNumber() + "'");

        ps.setString(1, subscriber.getFio());
        ps.setString(2, subscriber.getAddress());
        ps.setDate(3, new Date(subscriber.getRegDate().YEAR, subscriber.getRegDate().MONTH, subscriber.getRegDate().DAY_OF_MONTH));
        ps.execute();
    }
}
