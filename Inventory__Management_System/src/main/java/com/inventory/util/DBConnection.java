package com.inventory.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/inventoryDB";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection(){
        try {
            if (USER == null || PASSWORD == null) {
               System.out.println("Database credentials are not set in environment variables.");
            }
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Database connection failed: ");
        }
        return null;
    }

}
