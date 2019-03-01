package com.dac.spring.model.resp;

public class AdminCreateCategoryResponse {
    private int id;

    private String name;

    private String parentName;

    public AdminCreateCategoryResponse(int id, String name, String parentName) {
        this.id = id;
        this.name = name;
        this.parentName = parentName;
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

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
