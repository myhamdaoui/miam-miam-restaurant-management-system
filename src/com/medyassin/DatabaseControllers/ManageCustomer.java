package com.medyassin.DatabaseControllers;

import com.medyassin.Models.Customer;

import java.sql.*;
import java.util.ArrayList;

public class ManageCustomer {
    /**
     * Retrieve all customers
     * @return ArrayList<Customer>
     */
    public static ArrayList<Customer> getAllCustomers() throws SQLException, ClassNotFoundException {
        // Connection
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        // Result
        ArrayList<Customer> customers = new ArrayList<>();

        String query = "SELECT * FROM Customers";
        PreparedStatement stmt = conn.prepareStatement(query);

        ResultSet result = stmt.executeQuery();
        while(result.next()) {
            // Create a Customer
            Customer c = new Customer();
            c.setcID(result.getString("cID"));
            c.setcName(result.getString("cName"));
            c.setcPhoneN(result.getString("cPhoneN"));
            c.setcAddress(result.getString("cAddress"));

            // Add customer to the return result
            customers.add(c);
        }

        return customers;
    }

    /**
     * Add New Customer
     * @param name
     * @param phoneN
     * @param address
     * @return true = success
     * @throws SQLException
     * @throws ClassNotFoundException
     */

    public static boolean addNewCustomer(String name, String phoneN, String address) throws SQLException, ClassNotFoundException {
        // Connection
        Connection conn = DatabaseConnection.getDbConn().getConnection();

        // Result
        ArrayList<Customer> customers = new ArrayList<>();

        String query = "INSERT INTO Customers (cName, cPhoneN, cAddress) VALUES(?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setObject(1, name);
        stmt.setObject(2, phoneN);
        stmt.setObject(3, address);

        if(stmt.executeUpdate() <= 0) {
            return false;
        }

        return true;
    }
}
