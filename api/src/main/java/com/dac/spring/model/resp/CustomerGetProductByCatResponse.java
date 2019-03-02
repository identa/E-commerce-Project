package com.dac.spring.model.resp;

public class CustomerGetProductByCatResponse {
    private int id;

    private String name;

    private String description;

    private double originalPrice;

    private int discount;

    private String productImageURL;

    private String category;

    public CustomerGetProductByCatResponse(int id, String name, String description, double originalPrice, int discount, String productImageURL, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.originalPrice = originalPrice;
        this.discount = discount;
        this.productImageURL = productImageURL;
        this.category = category;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
