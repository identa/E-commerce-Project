package com.dac.spring.model.resp;

public class CustomerPaginateProductResponse {
    private int id;

    private String name;

    private double originalPrice;

    private int discount;

    private String productImageURL;

    public CustomerPaginateProductResponse(int id, String name, double originalPrice, int discount, String productImageURL) {
        this.id = id;
        this.name = name;
        this.originalPrice = originalPrice;
        this.discount = discount;
        this.productImageURL = productImageURL;
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

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getProductImageURL() {
        return productImageURL;
    }

    public void setProductImageURL(String productImageURL) {
        this.productImageURL = productImageURL;
    }
}
