package studentmanagementsystem.databases;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rainndev
 */

import java.sql.DriverManager;
import java.sql.Connection;


public class DatabaseConnection {
    public Connection getConnection () {
        Connection connection = null;
        try {
            String url = "jdbc:mysql://localhost/student_management_sys";
            String user = "root";
            String password = "";
            connection = DriverManager.getConnection(url, user, password);
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return connection;
    }
}
