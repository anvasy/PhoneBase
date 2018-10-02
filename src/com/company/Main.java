package com.company;

import com.company.db.DataBase;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.company.view.LoginFrame;
import com.company.view.OperatorFrame;

public class Main {

    public static void main(String[] args)  {
        try {
            new LoginFrame(new DataBase());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OperatorFrame.class.getName()).log(Level.SEVERE,
                    null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(OperatorFrame.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }
}
