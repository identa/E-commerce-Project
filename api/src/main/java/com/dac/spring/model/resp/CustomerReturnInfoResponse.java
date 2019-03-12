package com.dac.spring.model.resp;

public class CustomerReturnInfoResponse {
    private String role;

    private String imageURL;

    public CustomerReturnInfoResponse(String role, String imageURL) {
        this.role = role;
        this.imageURL = imageURL;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
