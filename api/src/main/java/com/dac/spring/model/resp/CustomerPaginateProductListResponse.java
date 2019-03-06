package com.dac.spring.model.resp;

import java.util.List;

public class CustomerPaginateProductListResponse {
    private int totalPages;

    private List<CustomerPaginateProductResponse> responses;

    public CustomerPaginateProductListResponse(int totalPages, List<CustomerPaginateProductResponse> responses) {
        this.totalPages = totalPages;
        this.responses = responses;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<CustomerPaginateProductResponse> getResponses() {
        return responses;
    }

    public void setResponses(List<CustomerPaginateProductResponse> responses) {
        this.responses = responses;
    }
}
