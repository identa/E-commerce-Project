package com.dac.spring.model.resp;

import com.dac.spring.entity.ImageEntity;

import java.util.List;

public class ProductDetailResponse {
    private int id;

    private String name;

    private double originalPrice;

    private double currentPrice;

    private String description;

    private int limit;

    private boolean inCart;

    private boolean inWishlist;

    private int quantity;

    private List<ImageResponse> images;

    private List<RatingResponse> ratings;

    public ProductDetailResponse(int id, String name, double originalPrice, double currentPrice, String description, int limit, boolean inCart, boolean inWishlist, int quantity, List<ImageResponse> images) {
        this.id = id;
        this.name = name;
        this.originalPrice = originalPrice;
        this.currentPrice = currentPrice;
        this.description = description;
        this.limit = limit;
        this.inCart = inCart;
        this.inWishlist = inWishlist;
        this.quantity = quantity;
        this.images = images;
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

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean isInCart() {
        return inCart;
    }

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }

    public boolean isInWishlist() {
        return inWishlist;
    }

    public void setInWishlist(boolean inWishlist) {
        this.inWishlist = inWishlist;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<ImageResponse> getImages() {
        return images;
    }

    public void setImages(List<ImageResponse> images) {
        this.images = images;
    }

    public List<RatingResponse> getRatings() {
        return ratings;
    }

    public void setRatings(List<RatingResponse> ratings) {
        this.ratings = ratings;
    }
}
