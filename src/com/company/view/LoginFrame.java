package com.company.view;

import com.company.db.DataBase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame{
    private JPanel panel;
    private JLabel labelLogin;
    private JLabel labelPassword;
    private JTextField tLogin;
    private JPasswordField pPassword;
    private JButton enter;
    private DataBase db;

    public LoginFrame(DataBase db) {
        this.db = db;
        setTitle("Вход");
        setSize(250, 160);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        action();
        setResizable(false);
        setVisible(true);
    }

    private void initComponents() {
        panel = new JPanel();
        labelLogin = new JLabel("Логин");
        labelPassword = new JLabel("Пароль");
        enter = new JButton("Вход");
        tLogin = new JTextField("tech", 20);
        pPassword = new JPasswordField("1111", 20);

        panel.add(labelLogin);
        panel.add(tLogin);
        panel.add(labelPassword);
        panel.add(pPassword);
        panel.add(enter);
        add(panel);
    }

    private void action() {
        enter.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (tLogin.getText().equals("")
                        || String.valueOf(pPassword.getPassword()).equals("")) {
                    JOptionPane.showMessageDialog(panel, "Заполните оба поля");
                } else {
                    try {

                        ResultSet rs = db
                                .query("select * from users where user_id='"
                                        + tLogin.getText() + "'");
                        if (rs.next()) {
                            if (rs.getString("password").equals(
                                    String.valueOf(pPassword.getPassword()))) {
                                    if (rs.getInt("role") == 0) {
                                        new TechnologistFrame(db);
                                    } else {
                                        new OperatorFrame(db);
                                    }
                                    dispose();
                            } else {
                                JOptionPane.showMessageDialog(panel,
                                        "Неверный логин или пароль");
                            }
                        } else {
                            JOptionPane.showMessageDialog(panel,
                                    "Неверный логин или пароль");
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(panel,
                                "Ошибка подключения к базе данных" + ex);
                    }
                }
            }
        });
    }
}
