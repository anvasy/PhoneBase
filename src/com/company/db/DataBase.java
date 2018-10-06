package com.company.db;

import java.sql.*;

public class DataBase {

    private Connection cn;
    private Statement st;

    public DataBase()
            throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/intercity_phone_calls?autoReconnect=true&useSSL=false";
        String user = "root";
        String password = "mrdrprpt";
        cn = DriverManager.getConnection(url, user, password);
        st = cn.createStatement();
    }

    public Connection getCn() {
        return cn;
    }

    public void update(String sql) throws SQLException {
        st.executeUpdate(sql);
    }

    public ResultSet query(String sql) throws SQLException {
        ResultSet rs = null;
        rs = st.executeQuery(sql);
        return rs;
    }

    public void close() throws SQLException {
        st.close();
        cn.close();
    }
}
