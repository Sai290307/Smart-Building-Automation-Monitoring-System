package com.SmartBuilding.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static Connection con = null;

    public static Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                // Load MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Database URL, username, password
                String url = "jdbc:mysql://localhost:3306/smartbuild";
                String user = "root";
                String password = "manager";

                // Establish connection
                con = DriverManager.getConnection(url, user, password);
                System.out.println("Database connected successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
