package com.company.view;

import com.company.dao.DaoServices;
import com.company.dao.DaoSubscribers;
import com.company.db.DataBase;
import com.company.model.Service;
import com.company.model.Subscriber;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class TechnologistFrame extends JFrame {

    private DataBase db;
    private DataTable subsTable;
    private DataTable servTable;
    private JScrollPane subsScroll;
    private JScrollPane servScroll;
    private JButton deleteSubscriber;
    private JButton deleteService;
    private JButton addSubscriber;
    private JButton addService;
    private JButton editSubscriber;
    private JButton editService;
    private JButton chooseSubscribers;
    private JButton showServices;
    private JTextField number;
    private JTextField address;
    private JTextField fio;
    private JTextField city;
    private JTextField cost;
    private JTextField privCost;
    private JTextField chooseCity;
    private JTextField chooseMonth;
    private UtilDateModel regModel;
    private JDatePanelImpl regPanel;
    private JDatePickerImpl regPicker;
    private UtilDateModel servModel;
    private JDatePanelImpl servPanel;
    private JDatePickerImpl servPicker;
    private UtilDateModel chooseDateModel;
    private JDatePanelImpl chooseDatePanel;
    private JDatePickerImpl chooseDatePicker;

    public TechnologistFrame(DataBase db){
        this.db = db;
        setTitle("Технолог");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        action();
        setResizable(false);
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel(null);
        JLabel subscribers = new JLabel("Абоненты: ");
        deleteSubscriber = new JButton("Удалить абонента");
        editSubscriber = new JButton("Редактировать абонента");
        try {
            subsTable = new DataTable(db.query("select * from subscribers"));
            subsScroll = new JScrollPane(subsTable);
        } catch (SQLException ex) {
            Logger.getLogger(OperatorFrame.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        JLabel services = new JLabel("Услуги: ");
        deleteService = new JButton("Удалить услугу");
        editService = new JButton("Редактировать услугу");
        try {
            servTable = new DataTable(db.query("select * from services"));
            servScroll = new JScrollPane(servTable);
        } catch (SQLException ex) {
            Logger.getLogger(OperatorFrame.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        number = new JTextField("Номер",20);
        fio = new JTextField("ФИО",40);
        address = new JTextField("Адрес",40);

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        regModel = new UtilDateModel();
        regPanel = new JDatePanelImpl(regModel, p);
        regPicker = new JDatePickerImpl(regPanel, new DateComponentFormatter());
        addSubscriber = new JButton("Добавить абонента");

        chooseDateModel = new UtilDateModel();
        chooseDatePanel = new JDatePanelImpl(chooseDateModel, p);
        chooseDatePicker = new JDatePickerImpl(chooseDatePanel, new DateComponentFormatter());
        showServices = new JButton("Показать услуги");

        city = new JTextField("Населённый пункт",20);
        cost = new JTextField("Стоимость",20);
        privCost = new JTextField("Льготная стоимость",20);

        servModel = new UtilDateModel();
        servPanel = new JDatePanelImpl(servModel, p);
        servPicker = new JDatePickerImpl(servPanel, new DateComponentFormatter());
        addService = new JButton("Добавить услугу");

        chooseCity = new JTextField("Город", 40);
        chooseMonth = new JTextField("Номер месяца", 40);
        chooseSubscribers = new JButton("Найти абонентов");


        subscribers.setBounds(50, 20, 100, 20);
        subsScroll.setBounds(50, 50, 800, 150);
        deleteSubscriber.setBounds(50, 205, 150, 30);
        editSubscriber.setBounds(250, 205, 200, 30);
        services.setBounds(50, 240, 100, 20);
        servScroll.setBounds(50, 270, 800, 150);
        deleteService.setBounds(50, 425, 150, 30);
        editService.setBounds(250, 425, 200, 30);
        number.setBounds(50, 480, 150, 20);
        fio.setBounds(50, 505, 150, 20);
        address.setBounds(50, 530, 150, 20);
        regPicker.setBounds(50, 555, 150, 30);
        addSubscriber.setBounds(50, 600, 150, 30);
        city.setBounds(260, 480, 150, 20);
        cost.setBounds(260, 505, 150, 20);
        privCost.setBounds(260, 530, 150, 20);
        servPicker.setBounds(260, 555, 150, 30);
        addService.setBounds(260, 600, 150, 30);
        chooseCity.setBounds(470, 480, 150, 20);
        chooseMonth.setBounds(470, 505, 150, 20);
        chooseSubscribers.setBounds(470, 540, 150, 30);
        chooseDatePicker.setBounds(680, 480, 150, 30);
        showServices.setBounds(680, 515, 150, 30);

        panel.add(subscribers);
        panel.add(subsScroll);
        panel.add(deleteSubscriber);
        panel.add(editSubscriber);
        panel.add(services);
        panel.add(servScroll);
        panel.add(deleteService);
        panel.add(editService);
        panel.add(number);
        panel.add(fio);
        panel.add(address);
        panel.add(regPicker);
        panel.add(addSubscriber);
        panel.add(city);
        panel.add(cost);
        panel.add(privCost);
        panel.add(servPicker);
        panel.add(addService);
        panel.add(chooseCity);
        panel.add(chooseMonth);
        panel.add(chooseSubscribers);
        panel.add(chooseDatePicker);
        panel.add(showServices);
        add(panel);
    }

    private void action() {
        deleteSubscriber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String number = "";
                    DaoSubscribers subs = new DaoSubscribers(db);
                    number = String.valueOf(subsTable.getValueAt(
                        subsTable.getSelectedRow(), 0));
                    subs.delete(new Subscriber(number));
                    subsTable.updateTable(db.query("select * from subscribers"));
                } catch (ArrayIndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "Выберите строку для удаления.",
                            "error", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "Error " + e1,
                            "error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        deleteService.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String city = "";
                    DaoServices serv = new DaoServices(db);
                    city = String.valueOf(servTable.getValueAt(
                        servTable.getSelectedRow(), 1));
                    serv.delete(new Service(city));
                    servTable.updateTable(db.query("select * from services"));
                } catch (ArrayIndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "Выберите строку для удаления.",
                            "error", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error " + ex,
                            "error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        addSubscriber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DaoSubscribers subs = new DaoSubscribers(db);
                    Calendar cal = Calendar.getInstance();
                    Date date = (Date) regPicker.getModel().getValue();
                    cal.setTime(date);          //?????????????????????????????????????????????????????????????????????
                    subs.insert(new Subscriber(number.getText(), fio.getText(),
                            address.getText(), cal));
                    subsTable.updateTable(db.query("select * from subscribers"));
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error " + ex,
                            "error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        addService.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DaoServices serv = new DaoServices(db);
                    Calendar cal = Calendar.getInstance();
                    Date date = (Date) servPicker.getModel().getValue();
                    cal.setTime(date);          //?????????????????????????????????????????????????????????????????????
                    serv.insert(new Service(cal, city.getText(), Float.valueOf(cost.getText()),
                            Float.valueOf(privCost.getText())));
                    servTable.updateTable(db.query("select * from services"));
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error " + ex,
                            "error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        editService.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Calendar cal = Calendar.getInstance();
                    String str = String.valueOf(servTable.getValueAt(
                        servTable.getSelectedRow(), 1));
                    cal.set(Integer.valueOf(str.substring(0, 4)), Integer.valueOf(str.substring(5, 7)),Integer.valueOf(str.substring(8, 10)));
                    Service service = new Service(cal, String.valueOf(servTable.getValueAt(
                        servTable.getSelectedRow(), 0)),
                        Float.valueOf(String.valueOf(servTable.getValueAt(
                                servTable.getSelectedRow(), 2))), Float.valueOf(String.valueOf(servTable.getValueAt(
                        servTable.getSelectedRow(), 3))));
                    DaoServices serv = new DaoServices(db);
                    serv.update(service);
                    servTable.updateTable(db.query("select * from services"));
                } catch (ArrayIndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "Выберите строку для редактирования.",
                            "error", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error " + ex,
                            "error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        editSubscriber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Calendar cal = Calendar.getInstance();
                    String str = String.valueOf(subsTable.getValueAt(subsTable.getSelectedRow(), 3));
                    cal.set(Integer.valueOf(str.substring(0, 4)), Integer.valueOf(str.substring(5, 7)),Integer.valueOf(str.substring(8, 10)));
                    Subscriber subscriber = new Subscriber(String.valueOf(subsTable.getValueAt(
                        subsTable.getSelectedRow(), 0)), String.valueOf(subsTable.getValueAt(
                        subsTable.getSelectedRow(), 1)), String.valueOf(subsTable.getValueAt(
                        subsTable.getSelectedRow(), 2)), cal);
                    DaoSubscribers subs = new DaoSubscribers(db);
                    subs.update(subscriber);
                    subsTable.updateTable(db.query("select * from subscribers"));
                } catch (ArrayIndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "Выберите строку для редактирования.",
                            "error", JOptionPane.INFORMATION_MESSAGE);
                } catch(SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error " + ex,
                            "error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        chooseSubscribers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = chooseCity.getText();
                int month = Integer.valueOf(chooseMonth.getText());
                try {
                    new ResultFrame(db.query("select call_date, number, fio from subscribers " +
                            "left join calls using (number) " +
                            "where calls.city = '" + city + "' AND MONTH(call_date) = " + month));
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error " + ex,
                            "error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        showServices.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
                    Date date = (Date) chooseDatePicker.getModel().getValue();
                    System.out.println(formatter.format(date));
                    ResultSet rs = db.query("select * from services where ser_date = '" + formatter.format(date) +"'");
                    new ResultFrame(rs);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error " + ex,
                            "error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
