package com.dac.spring.model.resp;

import java.util.List;

public class CustomerPaginateProductByCatResponse {

    private int totalPages;

    private List<CustomerGetProductByCatResponse> productList;

    public CustomerPaginateProductByCatResponse(int totalPages, List<CustomerGetProductByCatResponse> productList) {
        this.totalPages = totalPages;
        this.productList = productList;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<CustomerGetProductByCatResponse> getProductList() {
        return productList;
    }

    public void setProductList(List<CustomerGetProductByCatResponse> productList) {
        this.productList = productList;
    }
}
