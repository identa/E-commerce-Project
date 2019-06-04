package com.dac.spring.model.resp;

import java.util.List;

public class GetOrderResp {
    private String recipientName;

    private String city;

    private String address;

    private String state;

    private int postalCode;

    private int phone;

    private List<GetOrderResponse> orders;

    public GetOrderResp(String recipientName, String city, String address, String state, int postalCode, int phone, List<GetOrderResponse> orders) {
        this.recipientName = recipientName;
        this.city = city;
        this.address = address;
        this.state = state;
        this.postalCode = postalCode;
        this.phone = phone;
        this.orders = orders;
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

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public List<GetOrderResponse> getOrders() {
        return orders;
    }

    public void setOrders(List<GetOrderResponse> orders) {
        this.orders = orders;
    }
}
