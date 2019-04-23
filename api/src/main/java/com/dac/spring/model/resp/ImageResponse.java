package com.dac.spring.model.resp;

public class ImageResponse {
    private String imageURL;


    public ImageResponse(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
