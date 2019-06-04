package com.dac.spring.model.resp;

public class GetAddressResponse {
    private String recipientName;

    private String city;

    private String address;

    private String state;

    private int postalCode;

    private String phone;

    public GetAddressResponse() {
    }

    public GetAddressResponse(String recipientName, String city, String address, String state, int postalCode, String phone) {
        this.recipientName = recipientName;
        this.city = city;
        this.address = address;
        this.state = state;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
