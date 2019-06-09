package com.dac.spring.model.resp;

import java.util.List;

public class GetRatingResp {
    private Float avg;
    private Float rating;
    private String cmt;
    private List<RatingListResp> ratings;

    public GetRatingResp(Float avg, Float rating, String cmt, List<RatingListResp> ratings) {
        this.avg = avg;
        this.rating = rating;
        this.cmt = cmt;
        this.ratings = ratings;
    }

    public Float getAvg() {
        return avg;
    }

    public void setAvg(Float avg) {
        this.avg = avg;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getCmt() {
        return cmt;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }

    public List<RatingListResp> getRatings() {
        return ratings;
    }

    public void setRatings(List<RatingListResp> ratings) {
        this.ratings = ratings;
    }
}
