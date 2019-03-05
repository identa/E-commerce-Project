package com.dac.spring.model.resp;

public class CustomerCreateOrderDetailResponse {
    private int id;

    private double price;

    private int quantity;

    private String product;


    public CustomerCreateOrderDetailResponse(int id, double price, int quantity, String product) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.product = product;
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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
