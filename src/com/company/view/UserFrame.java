package com.company.view;

import com.company.dao.DaoCalls;
import com.company.db.DataBase;
import com.company.model.Call;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserFrame extends JFrame {

    private DataBase db;
    private String number;
    private DataTable userTable;
    private JScrollPane userScroll;
    private JButton pay;
    private JButton showUnpaid;

    public UserFrame(DataBase db, String number) {
        this.db = db;
        this.number = number;
        setTitle("Пользователь");
        setSize(900, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        action();
        setResizable(false);
        setVisible(true);
        alertUser();
    }

    private void initComponents() {
        JPanel panel = new JPanel(null);
        JLabel usersCalls = new JLabel("Ваши разговоры: ");
        try {
            userTable = new DataTable(db.query("select * from calls " +
                    "where number = '" + number + "'"), false);

            userScroll = new JScrollPane(userTable);
        } catch (SQLException ex) {
            Logger.getLogger(OperatorFrame.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        pay = new JButton("Оплатить");
        showUnpaid = new JButton("Неоплаченные");

        userScroll.setBounds(50, 50, 800, 150);
        pay.setBounds(50, 210, 150, 30);
        showUnpaid.setBounds(250, 210, 150, 30);

        panel.add(usersCalls);
        panel.add(userScroll);
        panel.add(pay);
        panel.add(showUnpaid);
        add(panel);
    }

    private void action() {
        pay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Call call = new Call(Integer.valueOf(String.valueOf(userTable.getValueAt(
                            userTable.getSelectedRow(), 0))));
                    DaoCalls calls = new DaoCalls(db);
                    calls.pay(call);
                    userTable.updateTable(db.query("select * from calls"));
                } catch(SQLException ex) {
                    Logger.getLogger(OperatorFrame.class.getName()).log(Level.SEVERE,
                            null, ex);
                }
            }
        });
        showUnpaid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new ResultFrame(db.query("select * from calls where is_paid = 0"));
                } catch (SQLException ex) {
                    Logger.getLogger(OperatorFrame.class.getName()).log(Level.SEVERE,
                            null, ex);
                }
            }
        });
    }

    private void alertUser() {
        long mills = System.currentTimeMillis();
        Date today = new Date(mills);
        int counter = 0;
        try {
            ResultSet rs = db.query("select number, fio, address, call_date, city from subscribers " +
                    "join calls using(number) where is_paid = 0");
            while (rs.next()) {
                Date callDate = rs.getDate(4);
                if (getDateDiff(callDate, today, TimeUnit.DAYS) > 20) {
                    counter++;
                }
            }
            if(counter > 0) {
                JOptionPane.showMessageDialog(UserFrame.this,"У вас " + counter + " задолженностей по звонкам.", "", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch(SQLException ex) {
            Logger.getLogger(OperatorFrame.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    private long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        System.out.println(timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS));
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

}
