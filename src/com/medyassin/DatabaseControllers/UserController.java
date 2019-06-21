package com.medyassin.DatabaseControllers;

import com.medyassin.Models.Customer;
import com.medyassin.Models.User;
import com.medyassin.TableViewModels.CustomerTVModel;
import com.medyassin.TableViewModels.UserTVModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public static String getUserRole() throws SQLException, ClassNotFoundException {
        /* SQL Query */
        String query = "SELECT role FROM users WHERE actif = 1";

        /* Ger DB Connection */
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        PreparedStatement stmt = conn.prepareStatement(query);

        ResultSet set = stmt.executeQuery();
        set.next();
        return set.getString("role");
    }

    public static ObservableList<UserTVModel> getAllUsers() throws SQLException, ClassNotFoundException {
        // Connection
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        // Result
        ObservableList<UserTVModel> users = FXCollections.observableArrayList();

        String query = "SELECT * FROM users WHERE actif = 0";
        PreparedStatement stmt = conn.prepareStatement(query);

        ResultSet result = stmt.executeQuery();
        while(result.next()) {
            // Create a Customer
            UserTVModel c = new UserTVModel("","", "","");
            c.setUserID(result.getString("id"));
            c.setUserName(result.getString("username"));
            c.setUserRole(result.getString("role"));
            c.setUserImg(result.getString("Image"));

            // Add customer to the return result
            users.add(c);
        }

        return users;
    }

    /**
     * Add New User
     * @param username
     * @param userRole
     * @param userImg
     * @return true = success
     * @throws SQLException
     * @throws ClassNotFoundException
     */

    public static boolean addNewUser(String username, String password, String userRole, String userImg) throws SQLException, ClassNotFoundException {
        // Connection
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        int maxId=getNextID();
        String query ="ALTER TABLE Users AUTO_INCREMENT = ?";
        PreparedStatement stmt=conn.prepareStatement(query);
        stmt.setObject(1,maxId);
        stmt.execute();

        query = "INSERT INTO Users (username, password, role, Image) VALUES(?, ?, ?, ?)";
        stmt = conn.prepareStatement(query);

        stmt.setObject(1, username);
        stmt.setObject(2, password);
        stmt.setObject(3, userRole);
        stmt.setObject(4, userImg);

        if(stmt.executeUpdate() <= 0) {
            return false;
        }

        return true;
    }

    public static boolean updateUser(int userID, String username,String password, String role, String image) throws SQLException, ClassNotFoundException {
        // Connection
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        String query = "UPDATE Users SET username = ?, password = ?, role = ?, Image = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setObject(1, username);
        stmt.setObject(2, password);
        stmt.setObject(3, role);
        stmt.setObject(4, image);
        stmt.setObject(5, userID);
        int count = stmt.executeUpdate();
        if(count <= 0) {
            return false;
        }
        return true;
    }

    public static int getNextID() throws SQLException, ClassNotFoundException {
        // Connection
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        String query = "SELECT id FROM Users ORDER BY id DESC LIMIT 1";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet res = stmt.executeQuery();
        res.next();
        return (Integer.parseInt(res.getString("id")) + 1);
    }

    public static boolean deleteUser(int id) throws SQLException, ClassNotFoundException {
        // Connection
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        String query = "DELETE FROM Users WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setObject(1, id);
        int count = stmt.executeUpdate();
        if(count <= 0) {
            return false;
        }

        return true;
    }

    public static  ObservableList<UserTVModel> searchForUsers(String name) throws SQLException, ClassNotFoundException {
        // Connection
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        // Result
        ObservableList<UserTVModel> users = FXCollections.observableArrayList();

        String query = "SELECT * FROM Users WHERE username LIKE ? and actif = 0";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setObject(1, "%" + name + "%");

        ResultSet result = stmt.executeQuery();
        while(result.next()) {
            // Create a Customer
            UserTVModel u = new UserTVModel("", "", "", "ahmed.jpg");
            u.setUserID(result.getString("id"));
            u.setUserName(result.getString("username"));
            u.setUserRole(result.getString("role"));
            u.setUserImg(result.getString("Image"));

            // Add customer to the return result
            users.add(u);
        }

        return users;
    }

}
