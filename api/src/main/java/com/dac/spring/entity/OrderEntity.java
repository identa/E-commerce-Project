package com.dac.spring.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product_order")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "statusID")
    private StatusEntity status;

    private boolean deleted= false;

    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "employeeID")
    private EmployeeEntity employee;

    @OneToMany(mappedBy = "order")
    private List<OrderDetailEntity> orderDetailEntityList;

    public OrderEntity() {
    }

    public OrderEntity(StatusEntity status, EmployeeEntity employee) {
        this.status = status;
        this.employee = employee;
    }

    public void setStatus(StatusEntity status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StatusEntity getStatus() {
        return status;
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

    public List<OrderDetailEntity> getOrderDetailEntityList() {
        return orderDetailEntityList;
    }

    public void setOrderDetailEntityList(List<OrderDetailEntity> orderDetailEntityList) {
        this.orderDetailEntityList = orderDetailEntityList;
    }
}
