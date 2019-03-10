package com.dac.spring.model.resp;

public class AdminGetOrderDetailResponse {
    private int id;

    private double price;

    private int quantity;

    private int productID;

    public AdminGetOrderDetailResponse(int id, double price, int quantity, int productID) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.productID = productID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }
}
