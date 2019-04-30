package com.dac.spring.model.resp;

public class WishlistData {
    private int id;

    private String imageURL;

    private String name;

    private double rating;

    private int totalRatings;

    private double price;

    private double cuttedPrice;

    public WishlistData(int id, String imageURL, String name, double rating, int totalRatings, double price, double cuttedPrice) {
        this.id = id;
        this.imageURL = imageURL;
        this.name = name;
        this.rating = rating;
        this.totalRatings = totalRatings;
        this.price = price;
        this.cuttedPrice = cuttedPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(int totalRatings) {
        this.totalRatings = totalRatings;
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
