package com.dac.spring.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "statusID")
    private StatusEntity status;

    private boolean deleted;

    private String description;

    private int quantity;

    private double originalPrice;

    private int discount;

    private int view;

    private String productImageURL;

    @ManyToOne
    @JoinColumn(name = "categoryID")
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "shopID")
    private EmployeeEntity shop;

    @OneToMany(mappedBy = "product")
    private List<OrderDetailEntity> orderDetailEntityList;

    public ProductEntity() {
    }

    public ProductEntity(String name, StatusEntity status, String description, int quantity, double originalPrice, int discount, String productImageURL, CategoryEntity category, EmployeeEntity shop) {
        this.name = name;
        this.status = status;
        this.description = description;
        this.quantity = quantity;
        this.originalPrice = originalPrice;
        this.discount = discount;
        this.productImageURL = productImageURL;
        this.category = category;
        this.shop = shop;
    }

    public ProductEntity(String name, StatusEntity status, String description, int quantity, double originalPrice, int discount, CategoryEntity category, EmployeeEntity shop) {
        this.name = name;
        this.status = status;
        this.description = description;
        this.quantity = quantity;
        this.originalPrice = originalPrice;
        this.discount = discount;
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

    public StatusEntity getStatus() {
        return status;
    }

    public void setStatus(StatusEntity status) {
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

    public List<OrderDetailEntity> getOrderDetailEntityList() {
        return orderDetailEntityList;
    }

    public void setOrderDetailEntityList(List<OrderDetailEntity> orderDetailEntityList) {
        this.orderDetailEntityList = orderDetailEntityList;
    }

    public EmployeeEntity getShop() {
        return shop;
    }

    public void setShop(EmployeeEntity shop) {
        this.shop = shop;
    }

    public String getProductImageURL() {
        return productImageURL;
    }

    public void setProductImageURL(String productImageURL) {
        this.productImageURL = productImageURL;
    }
}
