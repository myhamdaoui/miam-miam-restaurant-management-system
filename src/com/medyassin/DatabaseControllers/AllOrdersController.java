package com.medyassin.DatabaseControllers;

import com.medyassin.Models.Customer;
import com.medyassin.TableViewModels.ViewAllOrdersTVModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AllOrdersController {
    public static ObservableList<ViewAllOrdersTVModel> getAllOrders(String date) throws SQLException, ClassNotFoundException {
        // Connection
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        // Result
        ObservableList<ViewAllOrdersTVModel> list = FXCollections.observableArrayList();
        String query = "";
        if(date.equals("all")) {
            query = "SELECT OrderID, OrderDate, cName, OrderStatus FROM orders o, customers c WHERE o.CID = c.cID";
        } else {
            query = "SELECT OrderID, OrderDate, cName, OrderStatus FROM orders o, customers c WHERE o.CID = c.cID AND OrderDate = ?";
        }
        PreparedStatement stmt = conn.prepareStatement(query);

        if(!date.equals("all")) {
            stmt.setObject(1, date);
        }

        ResultSet result = stmt.executeQuery();
        while(result.next()) {
            // get total
            String query1 = "SELECT SUM(ItemPrice*itemQT) AS amount FROM orderdetails o, items i WHERE o.ItemCode = i.ItemCode AND o.OrderID = ?";
            PreparedStatement stmt1 = conn.prepareStatement(query1);
            stmt1.setObject(1, result.getString("OrderID"));
            ResultSet result1 = stmt1.executeQuery();

            // Create a Customer
            ViewAllOrdersTVModel o = new ViewAllOrdersTVModel();
            if(result1.next()) {
                o.setOrderAmount(result1.getString("amount"));
            }
            o.setOrderID(result.getString("OrderID"));
            o.setOrderDate(result.getString("OrderDate"));
            o.setClientName(result.getString("cName"));
            if(result.getString("OrderStatus").equals("1")) {
                o.setOrderStatus("Payé");
            } else {
                o.setOrderStatus("En cours");
            }

            // Add customer to the return result
            list.add(o);
        }

        return list;
    }
}
