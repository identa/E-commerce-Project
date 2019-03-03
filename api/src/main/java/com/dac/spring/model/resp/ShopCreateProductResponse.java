package com.dac.spring.model.resp;

public class ShopCreateProductResponse {
    private int id;

    private String name;

    private String status;

    private String description;

    private int quantity;

    private double originalPrice;

    private int discount;

    private String productImageURL;

    private String category;

    private String shop;

    public ShopCreateProductResponse(int id, String name, String status, String description, int quantity, double originalPrice, int discount, String productImageURL, String category, String shop) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.description = description;
        this.quantity = quantity;
        this.originalPrice = originalPrice;
        this.discount = discount;
        this.productImageURL = productImageURL;
        this.category = category;
        this.shop = shop;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }
}
