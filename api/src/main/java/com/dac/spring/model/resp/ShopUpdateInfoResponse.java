package com.dac.spring.model.resp;

public class ShopUpdateInfoResponse {
    private int id;

    private String firstName;

    private String lastName;

    private String imageURL;

    public ShopUpdateInfoResponse(int id, String firstName, String lastName, String imageURL) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
