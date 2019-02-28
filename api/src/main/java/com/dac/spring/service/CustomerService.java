package com.dac.spring.service;

import com.dac.spring.model.ServiceResult;

public interface CustomerService {
    ServiceResult signUpCustomer(String email, String password, String firstName, String lastName);
    ServiceResult signInCustomer(String email, String password);
    ServiceResult getCustomerById(int id);
    ServiceResult updateInfo(int id, String firstName, String lastName, String password, String imageURL);

}
