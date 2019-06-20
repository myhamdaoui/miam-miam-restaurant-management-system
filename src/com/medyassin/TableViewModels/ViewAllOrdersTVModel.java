package com.medyassin.TableViewModels;

import javafx.beans.property.SimpleStringProperty;

public class ViewAllOrdersTVModel {
    private SimpleStringProperty orderID, clientName, orderDate, orderAmount, orderStatus;

    public ViewAllOrdersTVModel(String orderID, String clientName, String orderDate, String orderAmount, String orderStatus) {
        this.orderID = new SimpleStringProperty(orderID);
        this.clientName = new SimpleStringProperty(clientName);
        this.orderDate = new SimpleStringProperty(orderDate);
        this.orderAmount = new SimpleStringProperty(orderAmount);
        this.orderStatus = new SimpleStringProperty(orderStatus);
    }

    public ViewAllOrdersTVModel() {
        this("", "", "", "", "");
    }

    public String getOrderID() {
        return orderID.get();
    }

    public SimpleStringProperty orderIDProperty() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID.set(orderID);
    }

    public String getClientName() {
        return clientName.get();
    }

    public SimpleStringProperty clientNameProperty() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName.set(clientName);
    }

    public String getOrderDate() {
        return orderDate.get();
    }

    public SimpleStringProperty orderDateProperty() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate.set(orderDate);
    }

    public String getOrderAmount() {
        return orderAmount.get();
    }

    public SimpleStringProperty orderAmountProperty() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount.set(orderAmount);
    }

    public String getOrderStatus() {
        return orderStatus.get();
    }

    public SimpleStringProperty orderStatusProperty() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus.set(orderStatus);
    }
}
