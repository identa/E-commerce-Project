package com.dac.spring.model.resp;

import java.util.List;

public class AdminPaginateOrderDetailResponse {
    private int totalPages;

    private List<AdminGetOrderDetailResponse> orderDetails;

    public AdminPaginateOrderDetailResponse(int totalPages, List<AdminGetOrderDetailResponse> orderDetails) {
        this.totalPages = totalPages;
        this.orderDetails = orderDetails;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<AdminGetOrderDetailResponse> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<AdminGetOrderDetailResponse> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
