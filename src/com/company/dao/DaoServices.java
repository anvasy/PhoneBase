package com.company.dao;

import com.company.db.DataBase;
import com.company.model.Service;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DaoServices implements Dao<Service> {

    private DataBase db;

    public DaoServices(DataBase db) {
        this.db = db;
    }

    @Override
    public void insert(Service service) throws SQLException {
        PreparedStatement ps = (PreparedStatement) db.getCn().prepareStatement(
                "insert into services (city,ser_date,cost,priv_cost)"
                        + "values(?,?,?,?)");

        ps.setString(1, service.getCity());
        ps.setDate(2, new java.sql.Date(service.getSerDate().getTimeInMillis()));
        ps.setFloat(3, service.getCost());
        ps.setFloat(4, service.getPrivCoast());
        ps.execute();
    }

    @Override
    public void delete(Service service) throws SQLException {
        db.update("delete from services where city = '" + service.getCity() + "'");
    }

    @Override
    public void update(Service service) throws SQLException {
        PreparedStatement ps = (PreparedStatement) db.getCn().prepareStatement(
                "update services set ser_date=?, cost=?, priv_cost=? where city='"
                        + service.getCity() + "'");

        ps.setDate(1, new java.sql.Date(service.getSerDate().getTimeInMillis()));
        ps.setFloat(2, service.getCost());
        ps.setFloat(3, service.getPrivCoast());
        ps.execute();
    }
}
