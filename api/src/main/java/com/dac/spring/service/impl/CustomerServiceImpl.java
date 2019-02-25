package com.dac.spring.service.impl;

import com.dac.spring.constant.CustomerSignInConst;
import com.dac.spring.constant.CustomerSignUpConst;
import com.dac.spring.entity.EmployeeEntity;
import com.dac.spring.entity.StatusEntity;
import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.enums.RoleName;
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

    private String authenticationWithJwt(String email, String password) {
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
                EmployeeEntity employee = new EmployeeEntity(firstName, lastName, email,
                        encoder.encode(password),
                        activeStatus);
                employee.setRole(roleRepository.findByName(RoleName.ROLE_CUSTOMER));
                employeeRepository.save(employee);
                String jwt = authenticationWithJwt(email, password);
                CustomerSignInSignUpResponse response = new CustomerSignInSignUpResponse(employee.getId(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getRole().getName().name(),
                        jwt);
                result.setMessage(CustomerSignUpConst.SUCCESS);
                result.setData(response);
            }
        }
        return result;
    }

    @Override
    public ServiceResult signInCustomer(String email, String password) {
        ServiceResult result = new ServiceResult();
        boolean isEmailExisted = employeeRepository.existsByEmail(email);
        if (!isEmailExisted) {
            result.setMessage(CustomerSignInConst.EMAIL_NOT_FOUND);
            result.setStatus(ServiceResult.Status.FAILED);
        } else {
            EmployeeEntity employee = employeeRepository.findByEmail(email);
            boolean isPasswordChecked = encoder.matches(password, employee.getPassword());
            if (isPasswordChecked) {

                String jwt = authenticationWithJwt(email, password);
                CustomerSignInSignUpResponse response = new CustomerSignInSignUpResponse(employee.getId(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getRole().getName().name(),
                        jwt);
                result.setMessage(CustomerSignInConst.SUCCESS);
                result.setData(response);
            } else {
                result.setStatus(ServiceResult.Status.FAILED);
                result.setMessage(CustomerSignInConst.EMAIL_PASSWORD_WRONG_FORMAT);
            }
        }

        return result;
    }
}
