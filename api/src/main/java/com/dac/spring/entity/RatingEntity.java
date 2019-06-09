package com.dac.spring.entity;

import javax.persistence.*;

@Entity
@Table(name = "rating")
public class RatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "userID")
    private EmployeeEntity user;

    @ManyToOne
    @JoinColumn(name = "productID")
    private ProductEntity product;

    private float rate;

    private String review;

    public RatingEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EmployeeEntity getUser() {
        return user;
    }

    public void setUser(EmployeeEntity user) {
        this.user = user;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
