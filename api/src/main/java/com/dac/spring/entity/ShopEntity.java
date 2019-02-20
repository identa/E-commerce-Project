package com.example.boot_demo.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shop")
public class ShopEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;

    private String password;

    private String name;

    private int phone;

    private String address;

    @OneToMany(mappedBy = "shop")
    private List<OrderEntity> orderEntityList;

    @OneToMany(mappedBy = "shop")
    private List<CampaignEntity> campaignEntityList;

    @OneToMany(mappedBy = "shop")
    private List<ProductEntity> productEntityList;

    public ShopEntity() {
    }

    public ShopEntity(String email, String password, String name, int phone, String address) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderEntity> getOrderEntityList() {
        return orderEntityList;
    }

    public List<CampaignEntity> getCampaignEntityList() {
        return campaignEntityList;
    }

    public void setCampaignEntityList(List<CampaignEntity> campaignEntityList) {
        this.campaignEntityList = campaignEntityList;
    }

    public void setOrderEntityList(List<OrderEntity> orderEntityList) {
        this.orderEntityList = orderEntityList;
    }

    public List<ProductEntity> getProductEntityList() {
        return productEntityList;
    }

    public void setProductEntityList(List<ProductEntity> productEntityList) {
        this.productEntityList = productEntityList;
    }
}
