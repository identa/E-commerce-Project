package com.dac.spring.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

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

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    private double budget;

    private double bid;

    private String imageURL;

    private String title;

    private String description;

    private String productURL;

    @Column(name = "spend")
    private double spend;

    @ManyToOne
    @JoinColumn(name = "shopID", nullable = false)
    private EmployeeEntity shop;

    public CampaignEntity() {
    }

    public CampaignEntity(String name, StatusEntity status, Date startDate, Date endDate, double budget, double bid, String imageURL) {
        this.name = name;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.bid = bid;
        this.imageURL = imageURL;
    }

    public CampaignEntity(String name, StatusEntity status, Date startDate, Date endDate, double budget, double bid) {
        this.name = name;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.bid = bid;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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

    public double getSpend() {
        return spend;
    }

    public void setSpend(double spend) {
        this.spend = spend;
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

    public EmployeeEntity getShop() {
        return shop;
    }

    public void setShop(EmployeeEntity shop) {
        this.shop = shop;
    }
}
