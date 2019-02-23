package com.dac.spring.service;

import com.dac.spring.model.ServiceResult;

public interface CustomerService {
    ServiceResult signUpCustomer(String email, String password, String firstName, String lastName);
}
