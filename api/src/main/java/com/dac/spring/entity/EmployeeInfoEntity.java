package com.dac.spring.entity;

import javax.persistence.*;

@Entity
@Table(name = "employee_info")
public class EmployeeInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String recipientName;

    private String city;

    private String address;

    private String state;

    private int postalCode;

    private String phone;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employeeID", nullable = false)
    private EmployeeEntity employee;

    public EmployeeInfoEntity() {
    }

    public EmployeeInfoEntity(String recipientName, String city, String address, String state, int postalCode, String phone, EmployeeEntity employee) {
        this.recipientName = recipientName;
        this.city = city;
        this.address = address;
        this.state = state;
        this.postalCode = postalCode;
        this.phone = phone;
        this.employee = employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }
}
