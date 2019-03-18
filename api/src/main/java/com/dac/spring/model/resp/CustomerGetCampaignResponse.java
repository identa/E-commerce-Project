package com.dac.spring.model.resp;

public class CustomerGetCampaignResponse {
    private String title;

    private String imageURL;

    private String productURL;

    public CustomerGetCampaignResponse(String title, String imageURL, String productURL) {
        this.title = title;
        this.imageURL = imageURL;
        this.productURL = productURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getProductURL() {
        return productURL;
    }

    public void setProductURL(String productURL) {
        this.productURL = productURL;
    }
}
