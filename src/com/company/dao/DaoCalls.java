package com.company.dao;

import com.company.db.DataBase;
import com.company.model.Call;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DaoCalls implements Dao<Call> {

    private DataBase db;

    public DaoCalls(DataBase db) {
        this.db = db;
    }

    @Override
    public void insert(Call call) throws SQLException {
        PreparedStatement ps = (PreparedStatement) db.getCn().prepareStatement(
                "insert into calls (call_id,call_date,city,number,duration)"
                        + "values(?,?,?,?,?)");

        ps.setInt(1, call.getCallId());
        ps.setDate(2, new Date(call.getCallDate().YEAR, call.getCallDate().MONTH, call.getCallDate().DAY_OF_MONTH));
        ps.setString(3, call.getCity());
        ps.setString(4, call.getNumber());
        ps.setInt(5, call.getDuration());
        ps.execute();
    }

    @Override
    public void delete(Call call) throws SQLException {
        db.update("delete from calls where call_id = " + call.getCallId());
    }

    @Override
    public void update(Call call) throws SQLException {
        PreparedStatement ps = (PreparedStatement) db.getCn().prepareStatement(
                "update calls set city=?, number=?, duration=? call_date=? where call_id = '"
                        + call.getCallId());
        ps.setString(1, call.getCity());
        ps.setString(2, call.getNumber());
        ps.setInt(3, call.getDuration());
        ps.setDate(4, new Date(call.getCallDate().YEAR, call.getCallDate().MONTH, call.getCallDate().DAY_OF_MONTH));
        ps.execute();
    }
}
