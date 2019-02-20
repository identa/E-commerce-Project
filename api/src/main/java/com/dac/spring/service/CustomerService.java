package com.dac.spring.service;

import com.dac.spring.model.ServiceResult;

public interface CustomerService {
    ServiceResult signInCustomer(String email, String password);
    ServiceResult signUpCustomer(String email, String password, String firstName, String lastName);
}