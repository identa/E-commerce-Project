package com.dac.spring.model.resp;

public class EditCartResponse {
    private int id;

    private double price;

    private double cuttedPrice;

    private int quantity;

    public EditCartResponse(int id, double price, double cuttedPrice, int quantity) {
        this.id = id;
        this.price = price;
        this.cuttedPrice = cuttedPrice;
        this.quantity = quantity;
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

    public double getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(double cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
