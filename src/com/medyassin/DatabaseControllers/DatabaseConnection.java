package com.medyassin.DatabaseControllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection conn;
    private static DatabaseConnection dbConn;
    private DatabaseConnection () throws ClassNotFoundException, SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost/restau_mana?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
    }

    public Connection getConnection() {
        return conn;
    }

    public static DatabaseConnection getDbConn() throws ClassNotFoundException, SQLException {
        if(dbConn==null) {
            dbConn = new DatabaseConnection();
        }

        return dbConn;
    }

}
