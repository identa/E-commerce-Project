package com.dac.spring.model.req;

import java.util.List;

public class AddOrderReq {
    private List<AddOrderRequest> orders;

    public List<AddOrderRequest> getOrders() {
        return orders;
    }

    public void setOrders(List<AddOrderRequest> orders) {
        this.orders = orders;
    }
}
