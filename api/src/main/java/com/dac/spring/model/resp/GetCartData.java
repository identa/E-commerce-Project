package com.dac.spring.model.resp;

public class GetCartData {
    private int id;

    private String name;

    private double price;

    private double cuttedPrice;

    private int quantity;

    private int limit;

    private String imageURL;

    public GetCartData(int id, String name, double price, double cuttedPrice, int quantity, String imageURL) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.cuttedPrice = cuttedPrice;
        this.quantity = quantity;
        this.imageURL = imageURL;
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

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
