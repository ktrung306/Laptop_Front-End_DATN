package com.hcm.sale_laptop.data.model.other;

public class AddressModel {
    private String userName;
    private String phoneNumber;
    private String address;
    private boolean isSelect;

    public AddressModel(String userName, String phoneNumber, String address, boolean isSelect) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isSelect = isSelect;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
