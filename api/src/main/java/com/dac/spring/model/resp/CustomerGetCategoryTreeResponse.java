package com.dac.spring.model.resp;

import java.util.List;

public class CustomerGetCategoryTreeResponse {
    private int id;

    private String name;

    private List<CustomerGetCategoryTreeResponse> subCategory;

    public CustomerGetCategoryTreeResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CustomerGetCategoryTreeResponse> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(List<CustomerGetCategoryTreeResponse> subCategory) {
        this.subCategory = subCategory;
    }
}
