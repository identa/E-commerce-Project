package com.dac.spring.model.resp;

public class GetOrderResponse {
    private int id;

    private int userID;

    public GetOrderResponse(int id, int userID) {
        this.id = id;
        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
