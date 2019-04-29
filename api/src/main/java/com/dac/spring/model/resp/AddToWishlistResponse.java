package com.dac.spring.model.resp;

public class AddToWishlistResponse {
    private int id;

    private int productID;

    private int userID;

    public AddToWishlistResponse(int id, int productID, int userID) {
        this.id = id;
        this.productID = productID;
        this.userID = userID;
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

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
