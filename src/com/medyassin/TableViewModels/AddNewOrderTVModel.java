package com.medyassin.TableViewModels;

import javafx.beans.property.SimpleStringProperty;

public class AddNewOrderTVModel {
    private SimpleStringProperty itemCode, itemName, itemPrice, itemQT, itemAmount;

    public AddNewOrderTVModel(String itemCode, String itemName, String itemPrice, String itemQT, String itemAmount) {
        this.itemCode = new SimpleStringProperty(itemCode);
        this.itemName = new SimpleStringProperty(itemName);
        this.itemPrice = new SimpleStringProperty(itemPrice);
        this.itemQT = new SimpleStringProperty(itemQT);
        this.itemAmount = new SimpleStringProperty(itemAmount);
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

    public String getItemQT() {
        return itemQT.get();
    }

    public SimpleStringProperty itemQTProperty() {
        return itemQT;
    }

    public void setItemQT(String itemQT) {
        this.itemQT.set(itemQT);
    }

    public String getItemAmount() {
        return itemAmount.get();
    }

    public SimpleStringProperty itemAmountProperty() {
        return itemAmount;
    }

    public void setItemAmount(String itemAmount) {
        this.itemAmount.set(itemAmount);
    }
}
