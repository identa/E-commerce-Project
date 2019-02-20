package com.example.boot_demo.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product_order")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String status;

    private boolean deleted;

    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "employeeID")
    private EmployeeEntity employee;

    @ManyToOne
    @JoinColumn(name = "shopID")
    private ShopEntity shop;

    @OneToMany(mappedBy = "order")
    private List<OrderDetailEntity> orderDetailEntityList;

    public OrderEntity() {
    }

    public OrderEntity(String status,
                       double totalPrice,
                       EmployeeEntity employee,
                       ShopEntity shop,
                       List<OrderDetailEntity> orderDetailEntityList) {
        this.status = status;
        this.totalPrice = totalPrice;
        this.employee = employee;
        this.shop = shop;
        this.orderDetailEntityList = orderDetailEntityList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }

    public ShopEntity getShop() {
        return shop;
    }

    public void setShop(ShopEntity shop) {
        this.shop = shop;
    }

    public List<OrderDetailEntity> getOrderDetailEntityList() {
        return orderDetailEntityList;
    }

    public void setOrderDetailEntityList(List<OrderDetailEntity> orderDetailEntityList) {
        this.orderDetailEntityList = orderDetailEntityList;
    }
}
