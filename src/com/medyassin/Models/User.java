package com.medyassin.Models;

import javafx.beans.property.SimpleStringProperty;

public class User {
    private String userID, userName, userRole, userImg;

    public User(String customerId, String customerName, String customerPhoneN, String customerAddress) {
        this.userID = customerId;
        this.userName = customerName;
        this.userRole = customerPhoneN;
        this.userImg = customerAddress;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }
}
