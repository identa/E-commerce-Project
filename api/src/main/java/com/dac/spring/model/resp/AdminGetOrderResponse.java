package com.dac.spring.model.resp;

public class AdminGetOrderResponse {
    private int id;

    private String status;

    private double totalPrice;

    private int customerID;

    public AdminGetOrderResponse(int id, String status, double totalPrice, int customerID) {
        this.id = id;
        this.status = status;
        this.totalPrice = totalPrice;
        this.customerID = customerID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
}
