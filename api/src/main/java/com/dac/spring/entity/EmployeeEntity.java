package com.dac.spring.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "employee")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    @Column(columnDefinition = "longtext")
    private String imageURL;

    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleID")
    private RoleEntity role;

    @ManyToOne
    @JoinColumn(name = "statusID")
    private StatusEntity status;

    @OneToMany(mappedBy = "employee")
    private List<OrderEntity> orderEntityList;

    @OneToMany(mappedBy = "shop")
    private List<ProductEntity> productEntityList;

    @OneToMany(mappedBy = "shop")
    private List<OrderEntity> orderShopList;

    @OneToMany(mappedBy = "shop")
    private List<CampaignEntity> campaignEntityList;

    public EmployeeEntity() {
    }

    public EmployeeEntity(String firstName, String lastName, String email, String password, StatusEntity status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public EmployeeEntity(String firstName, String lastName, String email, String password, StatusEntity status, RoleEntity role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.status = status;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public StatusEntity getStatus() {
        return status;
    }

    public void setStatus(StatusEntity status) {
        this.status = status;
    }

    public List<OrderEntity> getOrderEntityList() {
        return orderEntityList;
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

    public List<OrderEntity> getOrderShopList() {
        return orderShopList;
    }

    public void setOrderShopList(List<OrderEntity> orderShopList) {
        this.orderShopList = orderShopList;
    }

    public List<CampaignEntity> getCampaignEntityList() {
        return campaignEntityList;
    }

    public void setCampaignEntityList(List<CampaignEntity> campaignEntityList) {
        this.campaignEntityList = campaignEntityList;
    }
}
