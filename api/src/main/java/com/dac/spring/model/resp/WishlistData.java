package com.dac.spring.model.resp;

public class WishlistData {
    private int id;

    private int productID;

    private String name;

    private double price;

    private double cuttedPrice;

    public WishlistData(int id, int productID, String name, double price, double cuttedPrice) {
        this.id = id;
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.cuttedPrice = cuttedPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
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
}
