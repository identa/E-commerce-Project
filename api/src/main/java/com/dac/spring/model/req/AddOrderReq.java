package com.dac.spring.model.req;

import java.util.List;

public class AddOrderReq {
    private String recipientName;

    private String city;

    private String address;

    private String state;

    private int postalCode;

    private String phone;

    private String method;

    private List<AddOrderRequest> orders;

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

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<AddOrderRequest> getOrders() {
        return orders;
    }

    public void setOrders(List<AddOrderRequest> orders) {
        this.orders = orders;
    }
}
