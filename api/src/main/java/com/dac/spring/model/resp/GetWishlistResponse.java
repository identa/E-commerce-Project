package com.dac.spring.model.resp;

import java.util.List;

public class GetWishlistResponse {
    private int userID;

    private List<WishlistData> wishlist;

    public GetWishlistResponse(int userID, List<WishlistData> wishlist) {
        this.userID = userID;
        this.wishlist = wishlist;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public List<WishlistData> getWishlist() {
        return wishlist;
    }

    public void setWishlist(List<WishlistData> wishlist) {
        this.wishlist = wishlist;
    }
}
