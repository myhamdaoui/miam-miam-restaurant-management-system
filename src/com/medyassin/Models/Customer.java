package com.medyassin.Models;

public class Customer {
    private String cID, cName, cPhoneN, cAddress;

    public Customer(String cID, String cName, String cPhoneN, String cAddress) {
        this.cID = cID;
        this.cName = cName;
        this.cPhoneN = cPhoneN;
        this.cAddress = cAddress;
    }

    public Customer() {
        this("", "", "", "");
    }

    public String getcID() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcPhoneN() {
        return cPhoneN;
    }

    public void setcPhoneN(String cPhoneN) {
        this.cPhoneN = cPhoneN;
    }

    public String getcAddress() {
        return cAddress;
    }

    public void setcAddress(String cAddress) {
        this.cAddress = cAddress;
    }
}
