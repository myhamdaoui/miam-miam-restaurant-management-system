package com.medyassin.DatabaseControllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NewOrderController {

    public static int getNextID() throws SQLException, ClassNotFoundException {
        // Connection
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        String query = "SELECT orderID FROM Orders ORDER BY orderID DESC LIMIT 1";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet res = stmt.executeQuery();
        if(res.next()) {
            return (Integer.parseInt(res.getString("orderID")) + 1);
        }
        return 99999;
    }

    public static boolean addNewOrder(String orderDate, String cID, String orderStatus) throws SQLException, ClassNotFoundException{
        // Connection
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        String query = "INSERT INTO Orders (OrderDate, CID, OrderStatus) VALUES(?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setObject(1, orderDate);
        stmt.setObject(2, cID);
        stmt.setObject(3, orderStatus);

        if(stmt.executeUpdate() <= 0) {
            return false;
        }

        return true;
    }

    public static boolean addNewOrderDetails(String orderID, String itemCode, String itemQT) throws SQLException, ClassNotFoundException{
        // Connection
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        String query = "INSERT INTO orderdetails (OrderID, ItemCode, ItemQT) VALUES(?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setObject(1, orderID);
        stmt.setObject(2, itemCode);
        stmt.setObject(3, itemQT);

        if(stmt.executeUpdate() <= 0) {
            return false;
        }

        return true;
    }
}
