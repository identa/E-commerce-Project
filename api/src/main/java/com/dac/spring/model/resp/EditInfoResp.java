package com.dac.spring.model.resp;

public class EditInfoResp {
    private String firstName;
    private String lastName;
    private String photo;

    public EditInfoResp(String firstName, String lastName, String photo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
