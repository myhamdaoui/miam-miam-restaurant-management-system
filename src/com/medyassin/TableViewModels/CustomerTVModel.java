package com.medyassin.TableViewModels;

import javafx.beans.property.SimpleStringProperty;

public class CustomerTVModel {
    private SimpleStringProperty customerId, customerName, customerPhoneN, customerAddress;

    public  CustomerTVModel(String customerId, String customerName, String customerPhoneN, String customerAddress) {
        this.customerId = new SimpleStringProperty(customerId);
        this.customerName = new SimpleStringProperty(customerName);
        this.customerPhoneN = new SimpleStringProperty(customerPhoneN);
        this.customerAddress = new SimpleStringProperty(customerAddress);
    }

    public String getCustomerId() {
        return customerId.get();
    }

    public SimpleStringProperty customerIdProperty() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId.set(customerId);
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public SimpleStringProperty customerNameProperty() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public String getCustomerPhoneN() {
        return customerPhoneN.get();
    }

    public SimpleStringProperty customerPhoneNProperty() {
        return customerPhoneN;
    }

    public void setCustomerPhoneN(String customerPhoneN) {
        this.customerPhoneN.set(customerPhoneN);
    }

    public String getCustomerAddress() {
        return customerAddress.get();
    }

    public SimpleStringProperty customerAddressProperty() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress.set(customerAddress);
    }
}
