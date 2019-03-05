package com.dac.spring.entity;

import com.dac.spring.model.enums.StatusName;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "status")
public class StatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private StatusName name;

    @OneToMany(mappedBy = "status")
    private List<EmployeeEntity> employeeEntityList;

    @OneToMany(mappedBy = "status")
    private List<ProductEntity> productEntityList;

    @OneToMany(mappedBy = "status")
    private List<OrderEntity> orderEntityList;

    public StatusEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StatusName getName() {
        return name;
    }

    public void setName(StatusName name) {
        this.name = name;
    }

    public List<EmployeeEntity> getEmployeeEntityList() {
        return employeeEntityList;
    }

    public void setEmployeeEntityList(List<EmployeeEntity> employeeEntityList) {
        this.employeeEntityList = employeeEntityList;
    }

    public List<ProductEntity> getProductEntityList() {
        return productEntityList;
    }

    public void setProductEntityList(List<ProductEntity> productEntityList) {
        this.productEntityList = productEntityList;
    }

    public List<OrderEntity> getOrderEntityList() {
        return orderEntityList;
    }

    public void setOrderEntityList(List<OrderEntity> orderEntityList) {
        this.orderEntityList = orderEntityList;
    }
}
