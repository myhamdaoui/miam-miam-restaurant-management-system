package com.medyassin.DatabaseControllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    public static boolean login(String username, String password) throws SQLException, ClassNotFoundException {
        /* SQL Query */
        String query = "SELECT * FROM users WHERE username=?";

        /* Ger DB Connection */
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        /* Statement */
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setObject(1, username);
        ResultSet set = stmt.executeQuery();

        if(set.next()) {
            String pwd = set.getString("password");
            if(pwd.equals(password)) {
                return true;
            }
        }
        return false;
    }
}
