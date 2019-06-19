package com.medyassin.Models;

public class Order {
    private String orderID, orderDate, cID, orderStatus;

    public Order(String orderID, String orderDate, String cID, String orderStatus) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.cID = cID;
        this.orderStatus = orderStatus;
    }

    public Order() {
        this("", "", "", "");
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getcID() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
