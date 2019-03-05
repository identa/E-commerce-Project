package com.dac.spring.model.req;

import java.util.List;

public class CustomerCreateOrderRequest {
    private int customerID;

    private List<CustomerCreateOrderDetailRequest> orderDetailRequests;

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public List<CustomerCreateOrderDetailRequest> getOrderDetailRequests() {
        return orderDetailRequests;
    }

    public void setOrderDetailRequests(List<CustomerCreateOrderDetailRequest> orderDetailRequests) {
        this.orderDetailRequests = orderDetailRequests;
    }
}
