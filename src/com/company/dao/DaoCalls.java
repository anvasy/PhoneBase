package com.company.dao;

import com.company.db.DataBase;
import com.company.model.Call;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

public class DaoCalls implements Dao<Call> {

    private DataBase db;

    public DaoCalls(DataBase db) {
        this.db = db;
    }

    @Override
    public void insert(Call call) throws SQLException {
        PreparedStatement ps = (PreparedStatement) db.getCn().prepareStatement(
                "insert into calls (call_id,call_date,city,number,duration,call_time)"
                        + "values(?,?,?,?,?,?)");

        ps.setInt(1, call.getCallId());
        ps.setDate(2, new java.sql.Date(call.getCallDate().getTimeInMillis()));
        ps.setString(3, call.getCity());
        ps.setString(4, call.getNumber());
        ps.setInt(5, call.getDuration());
        ps.setTime(6, call.getTime());
        ps.execute();
    }

    @Override
    public void delete(Call call) throws SQLException {
        db.update("delete from calls where call_id = " + call.getCallId());
    }

    @Override
    public void update(Call call) throws SQLException {
        PreparedStatement ps = (PreparedStatement) db.getCn().prepareStatement(
                "update calls set call_date=?, call_time=?, city=?, number=?, duration=? where call_id = "
                        + call.getCallId());
        ps.setString(3, call.getCity());
        ps.setString(4, call.getNumber());
        ps.setInt(5, call.getDuration());
        ps.setDate(1, new java.sql.Date(call.getCallDate().getTimeInMillis()));
        ps.setTime(2, call.getTime());
        ps.execute();
    }

    public void pay(Call call) throws SQLException {
        PreparedStatement ps = (PreparedStatement) db.getCn().prepareStatement(
                "update calls set is_paid=1 where call_id = "
                        + call.getCallId());
        ps.execute();
    }
}
