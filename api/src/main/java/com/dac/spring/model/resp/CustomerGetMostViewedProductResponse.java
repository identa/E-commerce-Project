package com.dac.spring.model.resp;

public class CustomerGetMostViewedProductResponse {
    private int id;

    private String name;

    private String catName;

    private double price;

    private String imageURL;

    public CustomerGetMostViewedProductResponse(int id, String name, String catName, double price, String imageURL) {
        this.id = id;
        this.name = name;
        this.catName = catName;
        this.price = price;
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

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
