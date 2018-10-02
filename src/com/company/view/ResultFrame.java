package com.company.view;

import javax.swing.*;
import java.sql.ResultSet;

public class ResultFrame extends JFrame {

    private ResultSet rs;
    private DataTable table;

    public ResultFrame(ResultSet rs) {
        this.rs = rs;
        setTitle("Результат");
        setSize(900, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        setResizable(false);
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel(null);

        table = new DataTable(rs);
        JScrollPane scroll = new JScrollPane(table);

        scroll.setBounds(50, 50, 800, 150);

        panel.add(scroll);
        add(panel);
    }

    public void showDebtors(ResultSet rs) {
        table.debtors(rs);
    }
}
