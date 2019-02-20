package com.example.boot_demo.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String status;

    private boolean deleted;

    private String description;

    private int quantity;

    private double originalPrice;

    private int discount;

    private int view;

    @ManyToOne
    @JoinColumn(name = "categoryID")
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "shopID")
    private ShopEntity shop;

    @OneToMany(mappedBy = "product")
    private List<ProductImageEntity> productImageEntityList;

    @OneToMany(mappedBy = "product")
    private List<OrderDetailEntity> orderDetailEntityList;

    @OneToMany(mappedBy = "product")
    private List<CampaignEntity> campaignEntityList;

    public ProductEntity() {
    }

    public ProductEntity(String name, String status, boolean deleted, String description, double originalPrice, CategoryEntity category, ShopEntity shop) {
        this.name = name;
        this.status = status;
        this.deleted = deleted;
        this.description = description;
        this.originalPrice = originalPrice;
        this.category = category;
        this.shop = shop;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public List<ProductImageEntity> getProductImageEntityList() {
        return productImageEntityList;
    }

    public void setProductImageEntityList(List<ProductImageEntity> productImageEntityList) {
        this.productImageEntityList = productImageEntityList;
    }

    public List<OrderDetailEntity> getOrderDetailEntityList() {
        return orderDetailEntityList;
    }

    public void setOrderDetailEntityList(List<OrderDetailEntity> orderDetailEntityList) {
        this.orderDetailEntityList = orderDetailEntityList;
    }

    public List<CampaignEntity> getCampaignEntityList() {
        return campaignEntityList;
    }

    public void setCampaignEntityList(List<CampaignEntity> campaignEntityList) {
        this.campaignEntityList = campaignEntityList;
    }

    public ShopEntity getShop() {
        return shop;
    }

    public void setShop(ShopEntity shop) {
        this.shop = shop;
    }
}
