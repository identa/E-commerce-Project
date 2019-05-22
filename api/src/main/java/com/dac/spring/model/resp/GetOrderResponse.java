package com.dac.spring.model.resp;

public class GetOrderResponse {
    private int id;

    private int userID;

    private String createAt;

    public GetOrderResponse(int id, int userID, String createAt) {
        this.id = id;
        this.userID = userID;
        this.createAt = createAt;
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

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
