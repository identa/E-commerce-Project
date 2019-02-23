package com.dac.spring.service.impl;

import com.dac.spring.model.ServiceResult;
import com.dac.spring.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public ServiceResult signUpCustomer(String email, String password, String firstName, String lastName) {
        return null;
    }
}
