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
                String query1 = "UPDATE users SET actif = 1 WHERE id = ?";
                PreparedStatement stmt1 = conn.prepareStatement(query1);
                stmt1.setObject(1, set.getString("id"));
                stmt1.execute();
                return true;
            }
        }
        return false;
    }



    public static String getUserRole(String username, String password) throws SQLException, ClassNotFoundException{
        /* SQL Query */
        String query = "SELECT role FROM users WHERE username = ? AND password = ?";

        /* Ger DB Connection */
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        /* Statement */
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setObject(1, username);
        stmt.setObject(2, password);
        ResultSet set = stmt.executeQuery();

        if(set.next()) {
            return set.getString("role");
        }
        return "NO ROLE NO ERROR XD";
    }
}
