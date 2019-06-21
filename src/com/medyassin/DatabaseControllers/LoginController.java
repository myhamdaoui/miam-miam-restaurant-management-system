package com.medyassin.DatabaseControllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

        /* Make all user inactif */
        String query2 = "UPDATE users SET actif = 0";
        PreparedStatement stmt2 = conn.prepareStatement(query2);
        stmt2.execute();

        /* Statement */
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setObject(1, username);
        ResultSet set = stmt.executeQuery();

        String encryptedPassword = encryptePassword(password);

        if(set.next()) {
            String pwd = set.getString("password");
            if(pwd.equals(encryptedPassword)) {
                String query1 = "UPDATE users SET actif = 1 WHERE id = ?";
                PreparedStatement stmt1 = conn.prepareStatement(query1);
                stmt1.setObject(1, set.getString("id"));
                stmt1.execute();
                return true;
            }
        }
        return false;
    }

    public static String encryptePassword(String passwordToHash) {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }



    public static String getUserRole(String username) throws SQLException, ClassNotFoundException{
        /* SQL Query */
        String query = "SELECT role FROM users WHERE username = ?";

        /* Ger DB Connection */
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        /* Statement */
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setObject(1, username);
        ResultSet set = stmt.executeQuery();

        if(set.next()) {
            return set.getString("role");
        }
        return "NO ROLE NO ERROR XD";
    }
}
