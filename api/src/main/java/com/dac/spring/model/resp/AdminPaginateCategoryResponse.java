package com.dac.spring.model.resp;

import java.util.List;

public class AdminPaginateCategoryResponse {
    private int totalPages;

    private List<CustomerGetAllCategoryResponse> categoryList;

    public AdminPaginateCategoryResponse(int totalPages, List<CustomerGetAllCategoryResponse> categoryList) {
        this.totalPages = totalPages;
        this.categoryList = categoryList;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<CustomerGetAllCategoryResponse> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CustomerGetAllCategoryResponse> categoryList) {
        this.categoryList = categoryList;
    }
}
