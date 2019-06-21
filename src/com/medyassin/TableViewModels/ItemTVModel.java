package com.medyassin.TableViewModels;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


public class ItemTVModel {
    private SimpleStringProperty itemCode, itemName, itemPrice, itemType, itemImg;


    public ItemTVModel(String itemCode, String itemName, String itemPrice, String itemType, String itemImg) {
        this.itemCode = new SimpleStringProperty(itemCode);
        this.itemName = new SimpleStringProperty(itemName);
        this.itemPrice = new SimpleStringProperty(itemPrice);
        this.itemType = new SimpleStringProperty(itemType);
        this.itemImg = new SimpleStringProperty(itemImg);
    }

    public String getItemCode() {
        return itemCode.get();
    }

    public SimpleStringProperty itemCodeProperty() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode.set(itemCode);
    }

    public String getItemName() {
        return itemName.get();
    }

    public SimpleStringProperty itemNameProperty() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName.set(itemName);
    }

    public String getItemPrice() {
        return itemPrice.get();
    }

    public SimpleStringProperty itemPriceProperty() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice.set(itemPrice);
    }

    public String getItemType() {
        return itemType.get();
    }

    public SimpleStringProperty itemTypeProperty() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType.set(itemType);
    }

    public String getItemImg() {
        return itemImg.get();
    }

    public SimpleStringProperty itemImgProperty() {
        return itemImg;
    }

    public void setItemImg(String itemImg) {
        this.itemImg.set(itemImg);
    }
}
