package com.dac.spring.model.resp;

import java.util.List;

public class ShopPaginateProductByIdResponse {
    private int totalPages;

    private List<ShopGetProductResponse> productList;


    public ShopPaginateProductByIdResponse(int totalPages, List<ShopGetProductResponse> productList) {
        this.totalPages = totalPages;
        this.productList = productList;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ShopGetProductResponse> getProductList() {
        return productList;
    }

    public void setProductList(List<ShopGetProductResponse> productList) {
        this.productList = productList;
    }
}
