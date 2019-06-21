package com.medyassin.DatabaseControllers;

import com.medyassin.Models.Customer;
import com.medyassin.Models.Item;
import com.medyassin.TableViewModels.ItemTVModel;
import com.medyassin.TableViewModels.UserTVModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemController {
    public static ArrayList<String> getCategories() throws SQLException, ClassNotFoundException {
        // Connection
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        // Result
        ArrayList<String> categories = new ArrayList<>();


        String query = "SELECT DISTINCT ItemType FROM Items";
        PreparedStatement stmt = conn.prepareStatement(query);

        ResultSet result = stmt.executeQuery();
        while(result.next()) {
            categories.add(result.getString("ItemType"));
        }

        return categories;
    }

    public static int countTypes() throws SQLException, ClassNotFoundException {
        // Connection
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        // Result
        int i = 0;


        String query = "SELECT DISTINCT ItemType FROM Items";
        PreparedStatement stmt = conn.prepareStatement(query);

        ResultSet result = stmt.executeQuery();
        while(result.next()) {
            i++;
        }
        return i;
    }

    public static boolean deleteItem(int id) throws SQLException, ClassNotFoundException {
        // Connection
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        String query = "DELETE FROM Items WHERE ItemCode = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setObject(1, id);
        int count = stmt.executeUpdate();
        if(count <= 0) {
            return false;
        }

        return true;
    }

    public static ArrayList<String> getItemOfCat(String cat) throws SQLException, ClassNotFoundException {
        // Connection
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        // Result
        ArrayList<String> items = new ArrayList<>();


        String query = "SELECT itemName FROM Items WHERE ItemType = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setObject(1, cat);

        ResultSet result = stmt.executeQuery();
        while(result.next()) {
            items.add(result.getString("itemName"));
        }

        return items;
    }

    public static int getItemCodeFromName(String itemName) throws SQLException, ClassNotFoundException {
        // Connection
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        // Result
        int code = 1;


        String query = "SELECT ItemCode FROM Items WHERE ItemName = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setObject(1, itemName);

        ResultSet result = stmt.executeQuery();
        if(result.next()) {
            code = Integer.parseInt(result.getString("ItemCode"));
        }

        return code;
    }

    public static Item getItem(String itemCode) throws SQLException, ClassNotFoundException {
        // Connection
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        // Result
        Item item = null;


        String query = "SELECT * FROM Items WHERE ItemCode = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setObject(1, itemCode);

        ResultSet result = stmt.executeQuery();
        if(result.next()) {
            item = new Item();
            item.setItemCode(itemCode + "");
            item.setItemName(result.getString("ItemName"));
            item.setItemImage(result.getString("ItemImage"));
            item.setItemPrice(result.getString("ItemPrice"));
            item.setItemType(result.getString("ItemType"));
        }

        return item;
    }

    public static ObservableList<ItemTVModel> getAllItems(String itemType) throws SQLException, ClassNotFoundException {
        // Connection
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        // Result
        ObservableList<ItemTVModel> items = FXCollections.observableArrayList();

        String query = "";
        if(itemType.equals("all")) {
            query = "SELECT * FROM items";

        } else {
            query = "SELECT * FROM items WHERE ItemType = ?";
        }

        PreparedStatement stmt = conn.prepareStatement(query);

        if(!itemType.equals("all")) {
            stmt.setObject(1, itemType);
        }

        ResultSet result = stmt.executeQuery();
        while(result.next()) {
            // Create a Customer
            ItemTVModel i = new ItemTVModel("","", "","", "");
            i.setItemCode(result.getString("ItemCode"));
            i.setItemName(result.getString("ItemName"));
            i.setItemPrice(result.getString("ItemPrice"));
            i.setItemType(result.getString("ItemType"));
            i.setItemImg(result.getString("ItemImage"));

            // Add customer to the return result
            items.add(i);
        }

        return items;
    }

    public static int getNextID() throws SQLException, ClassNotFoundException {
        // Connection
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        String query = "SELECT ItemCode FROM items ORDER BY ItemCode DESC LIMIT 1";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet res = stmt.executeQuery();
        res.next();
        return (Integer.parseInt(res.getString("ItemCode")) + 1);
    }

    public static boolean addNewItem(String itemName, String itemPrice, String itemImage, String itemType) throws SQLException, ClassNotFoundException {
        // Connection
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        int maxId=getNextID();
        String query ="ALTER TABLE Items AUTO_INCREMENT = ?";
        PreparedStatement stmt=conn.prepareStatement(query);
        stmt.setObject(1,maxId);
        stmt.execute();

        query = "INSERT INTO Items (ItemName, ItemPrice, ItemImage, ItemType) VALUES(?, ?, ?, ?)";
        stmt = conn.prepareStatement(query);

        stmt.setObject(1, itemName);
        stmt.setObject(2, itemPrice);
        stmt.setObject(3, itemImage);
        stmt.setObject(4, itemType);

        if(stmt.executeUpdate() <= 0) {
            return false;
        }

        return true;
    }

    public static boolean updateItem(int itemCode, String itemName,String itemImage, String itemType, String itemPrice) throws SQLException, ClassNotFoundException {
        // Connection
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        String query = "UPDATE Items SET ItemName = ?, ItemImage = ?, ItemPrice = ?, ItemType = ? WHERE ItemCode = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setObject(1, itemName);
        stmt.setObject(2, itemImage);
        stmt.setObject(3, itemPrice);
        stmt.setObject(4, itemType);
        stmt.setObject(5, itemCode);
        int count = stmt.executeUpdate();
        if(count <= 0) {
            return false;
        }
        return true;
    }
}
