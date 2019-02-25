package com.dac.spring.service.impl;

import com.dac.spring.constant.CustomerSignUpConst;
import com.dac.spring.entity.EmployeeEntity;
import com.dac.spring.entity.StatusEntity;
import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.enums.RoleName;
import com.dac.spring.model.enums.StatusName;
import com.dac.spring.model.resp.AdminGetAllCustomerResponse;
import com.dac.spring.model.resp.AdminGetCustomerByIdResponse;
import com.dac.spring.model.resp.CustomerSignInSignUpResponse;
import com.dac.spring.repository.*;
import com.dac.spring.service.AdminService;
import com.dac.spring.utils.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

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

    private AdminGetAllCustomerResponse createGetAllCustomerResponse(EmployeeEntity entity) {
        AdminGetAllCustomerResponse response = new AdminGetAllCustomerResponse(entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getStatus().getName().name());
        return response;
    }

    private List<AdminGetAllCustomerResponse> createCustomerGetResponseList() {
        List<EmployeeEntity> customerList = employeeRepository.findByDeletedAndRoleName(false, RoleName.ROLE_CUSTOMER);
        List<AdminGetAllCustomerResponse> responseList = new ArrayList<>();
        for (EmployeeEntity entity : customerList) {
            responseList.add(createGetAllCustomerResponse(entity));
        }
        return responseList;
    }

    @Override
    public ServiceResult getAllCustomer() {
        ServiceResult result = new ServiceResult();
        List<AdminGetAllCustomerResponse> employeeEntityList = createCustomerGetResponseList();
        if (employeeEntityList != null) {
            result.setMessage("Get all customers successfully");
            result.setData(employeeEntityList);
        } else {
            result.setMessage("Get all customers failed");
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    @Override
    public ServiceResult getCustomerById(int id) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity employee = employeeRepository.findById(id).orElse(null);
        if (employee != null){
            AdminGetCustomerByIdResponse response = new AdminGetCustomerByIdResponse(employee.getFirstName(),
                    employee.getLastName(),
                    employee.getEmail(),
                    employee.getStatus().getName().name());
            result.setData(response);
            result.setMessage("ádas");
        }
        else {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage("ádasád");
        }
        return result;
    }

    @Override
    public ServiceResult updateCustomer(int id, String firstName, String lastName, String email, String password,
                                        StatusName statusName, RoleName roleName) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity employee = employeeRepository.findById(id).orElse(null);
        if (employee != null){
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setEmail(email);
            employee.setPassword(encoder.encode(password));
            employee.setStatus(statusRepository.findByName(statusName));
            employee.setRole(roleRepository.findByName(roleName));

            employeeRepository.save(employee);

            result.setMessage("Update customer successfully");
        }
        else {
            result.setMessage("Customer not found");
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    @Override
    public ServiceResult deleteCustomerById(int id) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity employee = employeeRepository.findById(id).orElse(null);
        if (employee != null){
            employee.setDeleted(true);
            employeeRepository.save(employee);
            result.setMessage("Delete customer successfully");
        }
        else {
            result.setMessage("Customer not found");
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    @Override
    public ServiceResult signUpAdmin(String firstName, String lastName, String email, String password) {
        ServiceResult result = new ServiceResult();
        StatusEntity activeStatus = statusRepository.findByName(StatusName.ACTIVE);
        boolean isEmailExisted = employeeRepository.existsByEmail(email);
        if (isEmailExisted) {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage(CustomerSignUpConst.EMAIL_EXIST);
        } else {
            if (firstName != null && lastName != null && email != null && password != null && activeStatus != null) {
                EmployeeEntity employee = new EmployeeEntity(firstName, lastName, email,
                        encoder.encode(password),
                        activeStatus);
                employee.setRole(roleRepository.findByName(RoleName.ROLE_ADMIN));
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
            else {
                result.setMessage(CustomerSignUpConst.NULL_DATA);
                result.setStatus(ServiceResult.Status.FAILED);
            }
        }
        return result;
    }
}
