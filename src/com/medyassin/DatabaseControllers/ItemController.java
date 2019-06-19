package com.medyassin.DatabaseControllers;

import com.medyassin.Models.Customer;
import com.medyassin.Models.Item;

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
        int code = 9999;


        String query = "SELECT ItemCode FROM Items WHERE ItemName = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setObject(1, itemName);

        ResultSet result = stmt.executeQuery();
        if(result.next()) {
            code = Integer.parseInt(result.getString("ItemCode"));
        }

        return code;
    }

    public static Item getItem(int itemCode) throws SQLException, ClassNotFoundException {
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
}