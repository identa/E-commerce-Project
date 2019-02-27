package com.dac.spring.model.resp;

import java.util.List;

public class AdminPaginateUserResponse {
    private int totalPages;

    private List<UserResponse> userResponseList;

    public AdminPaginateUserResponse(int totalPages, List<UserResponse> userResponseList) {
        this.totalPages = totalPages;
        this.userResponseList = userResponseList;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<UserResponse> getUserResponseList() {
        return userResponseList;
    }

    public void setUserResponseList(List<UserResponse> userResponseList) {
        this.userResponseList = userResponseList;
    }
}
