package com.dac.spring.service.impl;

import com.dac.spring.constants.CustomerSignInConst;
import com.dac.spring.constants.CustomerSignUpConst;
import com.dac.spring.entity.EmployeeEntity;
import com.dac.spring.entity.RoleEntity;
import com.dac.spring.entity.StatusEntity;
import com.dac.spring.model.CustomerSignInRequest;
import com.dac.spring.model.CustomerSignUpRequest;
import com.dac.spring.model.ServiceResult;
import com.dac.spring.repository.EmployeeRepository;
import com.dac.spring.repository.RoleRepository;
import com.dac.spring.repository.StatusRepository;
import com.dac.spring.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    StatusRepository statusRepository;

    @Override
    public ServiceResult signInCustomer(String email, String password) {
        ServiceResult result = new ServiceResult();

        EmployeeEntity employee = employeeRepository.findByEmailAndPassword(email, password).orElse(null);
        if (employee != null){
            result.setMessage(CustomerSignInConst.SUCCESS);
        }else {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage("Email or password is incorrect");
        }
        return result;
    }

    @Override
    public ServiceResult signUpCustomer(String email, String password, String firstName, String lastName) {
        ServiceResult result = new ServiceResult();
        RoleEntity customerRole = roleRepository.findById(1).orElse(null);
        StatusEntity activeStatus = statusRepository.findById(1).orElse(null);
        if (firstName != null && lastName != null && email != null && password != null
                && customerRole != null && activeStatus != null){
            EmployeeEntity employeeEntity = new EmployeeEntity(firstName,
                    lastName,
                    email,
                    password,
                    customerRole,
                    activeStatus);

            employeeRepository.save(employeeEntity);

            result.setMessage(CustomerSignUpConst.SUCCESS);
        }

        return result;
    }
}
