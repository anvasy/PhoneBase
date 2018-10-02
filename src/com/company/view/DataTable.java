package com.company.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.*;

public class DataTable extends JTable {

    private DefaultTableModel  dtm;


    public DataTable(ResultSet rs) {


        dtm = new DefaultTableModel() {

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return !(columnIndex == 0);
            }
        };

        try {
            ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                dtm.addColumn(rsmd.getColumnName(i));
            }
            while (rs.next()) {
                Vector v = new Vector();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    v.add(rs.getString(i));
                }
                dtm.addRow(v);
            }
            setModel(dtm);
            setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
        } catch (SQLException ex) {
            Logger.getLogger(DataTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateTable(ResultSet rs) {
        dtm.getDataVector().removeAllElements();
        try {
            ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
            while (rs.next()) {
                Vector v = new Vector();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    v.add(rs.getString(i));
                }
                dtm.addRow(v);
            }
            setModel(dtm);
            setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
        } catch (SQLException ex) {
            Logger.getLogger(DataTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        dtm.fireTableDataChanged();
    }


}
