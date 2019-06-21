package com.medyassin.TableViewModels;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


public class UserTVModel {
    private SimpleStringProperty userID, userName, userRole, userImg;
    private Rectangle userImgView;

    public UserTVModel(String userID, String userName, String userRole, String userImg) {
        this.userID = new SimpleStringProperty(userID);
        this.userName = new SimpleStringProperty(userName);
        this.userRole = new SimpleStringProperty(userRole);
        this.userImg = new SimpleStringProperty(userImg);
        this.userImgView = new Rectangle();
        userImgView.setHeight(50);
        userImgView.setWidth(50);
        userImgView.setArcHeight(10);
        userImgView.setArcWidth(10);
        //userImgView.setFill(Color.RED);
    }

    public String getUserID() {
        return userID.get();
    }

    public SimpleStringProperty userIDProperty() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID.set(userID);
    }

    public String getUserName() {
        return userName.get();
    }

    public SimpleStringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getUserRole() {
        return userRole.get();
    }

    public SimpleStringProperty userRoleProperty() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole.set(userRole);
    }

    public String getUserImg() {
        return userImg.get();
    }

    public SimpleStringProperty userImgProperty() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        userImgView.setFill(new ImagePattern(new Image("/com/medyassin/Img/" + userImg, false)));
        this.userImg.set(userImg);
    }

    public Rectangle getUserImgView() {
        return userImgView;
    }

    public void setUserImgView(Rectangle userImgView) {
        this.userImgView = userImgView;
    }
}
