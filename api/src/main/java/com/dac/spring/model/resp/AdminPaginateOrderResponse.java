package com.dac.spring.model.resp;

import java.util.List;

public class AdminPaginateOrderResponse {
    private int totalPages;

    private List<AdminGetOrderResponse> orders;

    public AdminPaginateOrderResponse(int totalPages, List<AdminGetOrderResponse> orders) {
        this.totalPages = totalPages;
        this.orders = orders;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<AdminGetOrderResponse> getOrders() {
        return orders;
    }

    public void setOrders(List<AdminGetOrderResponse> orders) {
        this.orders = orders;
    }
}
