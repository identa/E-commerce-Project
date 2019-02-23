package com.dac.spring.service.impl;

import com.dac.spring.constant.CustomerSignUpConst;
import com.dac.spring.entity.EmployeeEntity;
import com.dac.spring.entity.StatusEntity;
import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.resp.CustomerSignInSignUpResponse;
import com.dac.spring.repository.EmployeeRepository;
import com.dac.spring.repository.RoleRepository;
import com.dac.spring.repository.StatusRepository;
import com.dac.spring.service.CustomerService;
import com.dac.spring.utils.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PasswordEncoder encoder;

    private String jwtString(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        return jwt;
    }

    @Override
    public ServiceResult signUpCustomer(String email, String password, String firstName, String lastName) {
        ServiceResult result = new ServiceResult();
        StatusEntity activeStatus = statusRepository.findById(1).orElse(null);
        boolean isEmailExisted = employeeRepository.existsByEmail(email);
        if (isEmailExisted) {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage(CustomerSignUpConst.EMAIL_EXIST);
        } else {
            if (firstName != null && lastName != null && email != null && password != null && activeStatus != null) {
                EmployeeEntity employeeEntity = new EmployeeEntity(firstName,
                        lastName,
                        email,
                        encoder.encode(password),
                        activeStatus);
                employeeEntity.setRole(roleRepository.findById(1));
                employeeRepository.save(employeeEntity);
                String jwt = jwtString(email, password);
                CustomerSignInSignUpResponse response = new CustomerSignInSignUpResponse(employeeEntity.getId(),
                        employeeEntity.getFirstName(),
                        employeeEntity.getLastName(),
                        jwt);
                result.setMessage(CustomerSignUpConst.SUCCESS);
                result.setData(response);
            }

        }

        return result;
    }
}
