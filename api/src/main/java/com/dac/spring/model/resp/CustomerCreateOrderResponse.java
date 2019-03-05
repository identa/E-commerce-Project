package com.dac.spring.model.resp;

import java.util.List;

public class CustomerCreateOrderResponse {
    private int id;

    private double totalPrice;

    private int customerID;

    private List<CustomerCreateOrderDetailResponse> orderDetailResponses;

    public CustomerCreateOrderResponse(int id, double totalPrice, int customerID, List<CustomerCreateOrderDetailResponse> orderDetailResponses) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.customerID = customerID;
        this.orderDetailResponses = orderDetailResponses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<CustomerCreateOrderDetailResponse> getOrderDetailResponses() {
        return orderDetailResponses;
    }

    public void setOrderDetailResponses(List<CustomerCreateOrderDetailResponse> orderDetailResponses) {
        this.orderDetailResponses = orderDetailResponses;
    }
}
