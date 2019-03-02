package com.dac.spring.service;

import com.dac.spring.model.ServiceResult;

public interface CustomerService {
    ServiceResult signUpCustomer(String email, String password, String firstName, String lastName);
    ServiceResult signIn(String email, String password);
    ServiceResult getInfoById(int id);
    ServiceResult updateInfo(int id, String firstName, String lastName, String password, String imageURL);
    ServiceResult getCategoryTree();
    ServiceResult getAllCategory();
    ServiceResult paginateProductByCat(int id, int page, int size);

}
