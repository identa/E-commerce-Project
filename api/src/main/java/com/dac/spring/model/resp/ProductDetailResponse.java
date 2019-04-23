package com.dac.spring.model.resp;

import com.dac.spring.entity.ImageEntity;

import java.util.List;

public class ProductDetailResponse {
    private int id;

    private String name;

    private double originalPrice;

    private int discount;

    private String description;

    private int limit;

    private boolean inCart;

    private List<ImageResponse> images;

    public ProductDetailResponse(int id, String name, double originalPrice, int discount, String description, int limit, boolean inCart, List<ImageResponse> images) {
        this.id = id;
        this.name = name;
        this.originalPrice = originalPrice;
        this.discount = discount;
        this.description = description;
        this.limit = limit;
        this.inCart = inCart;
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

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
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

    public List<ImageResponse> getImages() {
        return images;
    }

    public void setImages(List<ImageResponse> images) {
        this.images = images;
    }
}
