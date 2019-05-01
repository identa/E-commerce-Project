package com.dac.spring.model.resp;

public class AddOrderResponse {
    private int id;

    public AddOrderResponse(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
