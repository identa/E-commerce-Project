package com.dac.spring.model.resp;

import java.util.List;

public class GetCartResponse {
    private double totalPrice;

    private int totalItem;

    private int itemAmount;

    private List<GetCartData> cartData;

    public GetCartResponse(double totalPrice, int totalItem, int itemAmount, List<GetCartData> cartData) {
        this.totalPrice = totalPrice;
        this.totalItem = totalItem;
        this.itemAmount = itemAmount;
        this.cartData = cartData;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public int getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
    }

    public List<GetCartData> getCartData() {
        return cartData;
    }

    public void setCartData(List<GetCartData> cartData) {
        this.cartData = cartData;
    }
}
