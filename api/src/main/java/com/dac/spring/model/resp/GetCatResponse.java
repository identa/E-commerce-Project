package com.dac.spring.model.resp;

public class GetCatResponse {
    private int id;

    private String imageURL;

    private String name;

    public GetCatResponse(int id, String imageURL, String name) {
        this.id = id;
        this.imageURL = imageURL;
        this.name = name;
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
}
