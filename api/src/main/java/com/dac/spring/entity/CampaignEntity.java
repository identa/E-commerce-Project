package com.dac.spring.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

@Entity
@Table(name = "campaign")
public class CampaignEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "statusID")
    private StatusEntity status;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss")
    private DateTimeFormat startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss")
    private DateTimeFormat endDate;

    private double budget;

    private double bid;

    private String imageURL;

    private String title;

    private String description;

    private String productURL;

    public CampaignEntity() {
    }

    public CampaignEntity(String name, StatusEntity status, DateTimeFormat startDate, DateTimeFormat endDate, double budget, double bid, String imageURL, String title, String description, String productURL) {
        this.name = name;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.bid = bid;
        this.imageURL = imageURL;
        this.title = title;
        this.description = description;
        this.productURL = productURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StatusEntity getStatus() {
        return status;
    }

    public void setStatus(StatusEntity status) {
        this.status = status;
    }

    public DateTimeFormat getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTimeFormat startDate) {
        this.startDate = startDate;
    }

    public DateTimeFormat getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTimeFormat endDate) {
        this.endDate = endDate;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductURL() {
        return productURL;
    }

    public void setProductURL(String productURL) {
        this.productURL = productURL;
    }
}
