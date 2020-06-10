package ru.javawebinar.topjava.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbUtil {

    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost/mealsdb?serverTimezone=Europe/Moscow&useSSL=false";
            String user = "root";
            String password = "root";
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}

