package com.medyassin.DatabaseControllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {
    public static String getUserImg() throws SQLException, ClassNotFoundException {
        /* SQL Query */
        String query = "SELECT Image FROM users WHERE actif = 1";

        /* Ger DB Connection */
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        PreparedStatement stmt = conn.prepareStatement(query);

        ResultSet set = stmt.executeQuery();
        set.next();
        return set.getString("Image");
    }
    public static String getUserName() throws SQLException, ClassNotFoundException {
        /* SQL Query */
        String query = "SELECT username FROM users WHERE actif = 1";

        /* Ger DB Connection */
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        PreparedStatement stmt = conn.prepareStatement(query);

        ResultSet set = stmt.executeQuery();
        set.next();
        return set.getString("username");
    }

}
