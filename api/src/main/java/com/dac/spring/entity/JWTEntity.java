package com.dac.spring.entity;

import javax.persistence.*;

@Entity
@Table(name = "jwt")
public class JWTEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int id;

    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
