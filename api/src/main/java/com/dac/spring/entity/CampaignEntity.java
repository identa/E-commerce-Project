package com.dac.spring.entity;

import javax.persistence.*;

@Entity
@Table(name = "campaign")
public class CampaignEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "shopID")
    private EmployeeEntity shop;

    @ManyToOne
    @JoinColumn(name = "productID")
    private ProductEntity product;

    public CampaignEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EmployeeEntity getShop() {
        return shop;
    }

    public void setShop(EmployeeEntity shop) {
        this.shop = shop;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }
}
