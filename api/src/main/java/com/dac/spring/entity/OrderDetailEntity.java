package com.example.boot_demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "order_detail")
public class OrderDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double price;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "productID")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "orderID")
    private OrderEntity order;

    public OrderDetailEntity() {
    }

    public OrderDetailEntity(double price, int quantity, ProductEntity product, OrderEntity order) {
        this.price = price;
        this.quantity = quantity;
        this.product = product;
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}
