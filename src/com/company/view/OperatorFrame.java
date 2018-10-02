package com.company.view;

import com.company.dao.DaoCalls;
import com.company.db.DataBase;
import com.company.model.Call;
import com.company.model.Subscriber;
import jdk.nashorn.internal.ir.TryNode;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OperatorFrame extends JFrame {

    private DataBase db;
    private DataTable callsTable;
    private JScrollPane scroll;
    private JButton deleteCall;
    private JButton editCall;
    private JButton addCall;
    private JTextField city;
    private JTextField number;
    private JTextField duration;
    private UtilDateModel callModel;
    private JDatePanelImpl callPanel;
    private JDatePickerImpl callPicker;

    public OperatorFrame(DataBase db) {
        this.db = db;
        setTitle("Оператор");
        setSize(900, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        action();
        setResizable(false);
        setVisible(true);
   }

   private void initComponents() {
        JPanel panel = new JPanel(null);
        JLabel calls = new JLabel("Разговоры: ");
        deleteCall = new JButton("Удалить разговор");
        editCall = new JButton("Редактировать разговор");
        try {
            callsTable = new DataTable(db.query("select * from calls"));
            scroll = new JScrollPane(callsTable);
        } catch (SQLException ex) {
            Logger.getLogger(OperatorFrame.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        callModel = new UtilDateModel();
        callPanel = new JDatePanelImpl(callModel, p);
        callPicker = new JDatePickerImpl(callPanel, new DateComponentFormatter());
        city = new JTextField("Город",40);
        number = new JTextField("Номер",40);
        duration = new JTextField("Продолжтельность",40);
        addCall = new JButton("Добавить разговор");

        scroll.setBounds(50, 50, 800, 150);
        calls.setBounds(50, 20, 100, 20);
        deleteCall.setBounds(50, 205, 150, 30);
        editCall.setBounds(250, 205, 200, 30);
        callPicker.setBounds(50, 255, 150, 30);
        city.setBounds(50, 290, 150, 20);
        number.setBounds(50, 315, 150, 20);
        duration.setBounds(50, 340, 150, 20);
        addCall.setBounds(50, 375, 150, 30);

        panel.add(calls);
        panel.add(scroll);
        panel.add(deleteCall);
        panel.add(editCall);
        panel.add(callPicker);
        panel.add(city);
        panel.add(number);
        panel.add(duration);
        panel.add(addCall);
        add(panel);
    }

   private void action() {
        deleteCall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Call call = new Call(Integer.valueOf(String.valueOf(callsTable.getValueAt(
                            callsTable.getSelectedRow(), 0))));
                    DaoCalls calls = new DaoCalls(db);
                    calls.delete(call);
                    callsTable.updateTable(db.query("select * from calls"));
                    System.out.println("here");
                } catch (ArrayIndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "Выберите строку для удаления.",
                            "error", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error " + ex,
                            "error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
       editCall.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               try {
                   DaoCalls calls = new DaoCalls(db);
                   calls.update(callFromTable(callsTable.getSelectedRow()));
                   callsTable.updateTable(db.query("select * from calls"));
               } catch (ArrayIndexOutOfBoundsException ex) {
                   JOptionPane.showMessageDialog(null, "Выберите строку для редактирования.",
                           "error", JOptionPane.INFORMATION_MESSAGE);
               } catch (SQLException ex) {
                   JOptionPane.showMessageDialog(null, "Error " + ex,
                           "error", JOptionPane.ERROR_MESSAGE);
               }
           }
       });
       addCall.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               try {
                   int maxId = 0;
                   ResultSet rs = db.query("select max(call_id) from calls");
                   if(rs.next()){
                       maxId=rs.getInt(1);
                   }
                   Calendar cal = Calendar.getInstance();
                   Date date = (Date) callPicker.getModel().getValue();
                   cal.setTime(date);
                   Call call = new Call(maxId + 1, cal, city.getText(),
                           number.getText(), Integer.valueOf(duration.getText()));
                   DaoCalls calls = new DaoCalls(db);
                   calls.insert(call);
                   callsTable.updateTable(db.query("select * from calls"));
               } catch (SQLException ex) {
                   JOptionPane.showMessageDialog(null, "Error " + ex,
                           "error", JOptionPane.ERROR_MESSAGE);
               }
           }
       });
   }

   private Call callFromTable(int row) {
       Calendar cal = Calendar.getInstance();
       String str = String.valueOf(callsTable.getValueAt(row, 0));
       cal.set(Integer.valueOf(str.substring(0, 4)), Integer.valueOf(str.substring(5, 7)),Integer.valueOf(str.substring(8, 10)));
        Call call = new Call(cal, String.valueOf(callsTable.getValueAt(
                row, 1)), String.valueOf(callsTable.getValueAt(
                row, 2)), Integer.valueOf(String.valueOf(callsTable.getValueAt(
                row, 3))));
        return call;
   }
}
