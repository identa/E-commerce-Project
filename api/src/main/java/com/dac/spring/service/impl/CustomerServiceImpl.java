package com.dac.spring.service.impl;

import com.dac.spring.constants.CustomerSignInConst;
import com.dac.spring.constants.CustomerSignUpConst;
import com.dac.spring.entity.EmployeeEntity;
import com.dac.spring.entity.RoleEntity;
import com.dac.spring.entity.RoleName;
import com.dac.spring.entity.StatusEntity;
import com.dac.spring.model.CustomerSignUpResponse;
import com.dac.spring.model.ServiceResult;
import com.dac.spring.repository.EmployeeRepository;
import com.dac.spring.repository.RoleRepository;
import com.dac.spring.repository.StatusRepository;
import com.dac.spring.security.jwt.JwtProvider;
import com.dac.spring.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    private String encode(String str) {
        String result = "";
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(str.getBytes());
            BigInteger bigInteger = new BigInteger(1, digest.digest());
            result = bigInteger.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String jwtString(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        return jwt;
    }

    @Override
    public ServiceResult signInCustomer(String email, String password) {
        ServiceResult result = new ServiceResult();
        boolean isEmailExisted = employeeRepository.existsByEmail(email);
        if (!isEmailExisted) {
            result.setMessage(CustomerSignInConst.EMAIL_NOT_FOUND);
            result.setStatus(ServiceResult.Status.FAILED);
        } else {
//            String encryptedPassword = encode(password);
            EmployeeEntity employee = employeeRepository.findByEmail(email);
            boolean isPasswordChecked = encoder.matches(password, employee.getPassword());
            if (isPasswordChecked) {

//                Authentication authentication = authenticationManager.authenticate(
//                        new UsernamePasswordAuthenticationToken(
//                                email,
//                                password
//                        )
//                );
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//
//                String jwt = jwtProvider.generateJwtToken(authentication);
                String jwt = jwtString(email, password);
                CustomerSignUpResponse response = new CustomerSignUpResponse(employee.getId(),
                        employee.getFirstName(),
                        employee.getLastName(),
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
                employeeEntity.setRole(roleRepository.findByName(RoleName.ROLE_CUSTOMER));
                employeeRepository.save(employeeEntity);
                String jwt = jwtString(email, password);
                CustomerSignUpResponse response = new CustomerSignUpResponse(employeeEntity.getId(),
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
