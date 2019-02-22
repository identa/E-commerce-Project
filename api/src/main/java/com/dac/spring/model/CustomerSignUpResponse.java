package com.dac.spring.model;

public class CustomerSignUpResponse {
    private int id;

    private String firstName;

    private String lastName;

    private String token;

    public CustomerSignUpResponse(int id, String firstName, String lastName, String token) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.token = token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
