package com.dac.spring.model.resp;

public class GetRatingResp {
    private int total1;
    private int total2;
    private int total3;
    private int total4;
    private int total5;
    private int total;
    private float avg;
    private Integer rating;

    public GetRatingResp(int total1, int total2, int total3, int total4, int total5, int total, float avg, Integer rating) {
        this.total1 = total1;
        this.total2 = total2;
        this.total3 = total3;
        this.total4 = total4;
        this.total5 = total5;
        this.total = total;
        this.avg = avg;
        this.rating = rating;
    }

    public int getTotal1() {
        return total1;
    }

    public void setTotal1(int total1) {
        this.total1 = total1;
    }

    public int getTotal2() {
        return total2;
    }

    public void setTotal2(int total2) {
        this.total2 = total2;
    }

    public int getTotal3() {
        return total3;
    }

    public void setTotal3(int total3) {
        this.total3 = total3;
    }

    public int getTotal4() {
        return total4;
    }

    public void setTotal4(int total4) {
        this.total4 = total4;
    }

    public int getTotal5() {
        return total5;
    }

    public void setTotal5(int total5) {
        this.total5 = total5;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public float getAvg() {
        return avg;
    }

    public void setAvg(float avg) {
        this.avg = avg;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
