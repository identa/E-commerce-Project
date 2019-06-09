package com.dac.spring.model.resp;

public class RatingListResp {
    private String name;
    private float rating;
    private String cmt;

    public RatingListResp(String name, float rating, String cmt) {
        this.name = name;
        this.rating = rating;
        this.cmt = cmt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getCmt() {
        return cmt;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }
}
