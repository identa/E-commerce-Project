package com.dac.spring.model.resp;

public class GetOrderResponse {
    private int id;

    private int userID;

    private String method;

    private String createAt;

    public GetOrderResponse(int id, int userID, String method, String createAt) {
        this.id = id;
        this.userID = userID;
        this.method = method;
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

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
