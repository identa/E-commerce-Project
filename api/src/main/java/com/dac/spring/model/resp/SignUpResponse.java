package com.dac.spring.model.resp;

public class SignUpResponse {
    private int id;

    private String firstName;

    private String lastName;

    private String role;

    private String imageURL;

    private String token;

    private boolean haveAddress;

    public SignUpResponse(int id, String firstName, String lastName, String role, String imageURL, String token, boolean haveAddress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.imageURL = imageURL;
        this.token = token;
        this.haveAddress = haveAddress;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isHaveAddress() {
        return haveAddress;
    }

    public void setHaveAddress(boolean haveAddress) {
        this.haveAddress = haveAddress;
    }
}
