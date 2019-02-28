package com.dac.spring.service.impl;

import com.dac.spring.constant.CustomerSignInConst;
import com.dac.spring.constant.CustomerSignUpConst;
import com.dac.spring.entity.EmployeeEntity;
import com.dac.spring.entity.StatusEntity;
import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.enums.RoleName;
import com.dac.spring.model.enums.StatusName;
import com.dac.spring.model.resp.CustomerGetInfoResponse;
import com.dac.spring.model.resp.CustomerSignInSignUpResponse;
import com.dac.spring.model.resp.CustomerUpdateInfoResponse;
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

        return jwtProvider.generateJwtToken(authentication);
    }

    @Override
    public ServiceResult signUpCustomer(String email, String password, String firstName, String lastName) {
        ServiceResult result = new ServiceResult();
        StatusEntity activeStatus = statusRepository.findByName(StatusName.ACTIVE);
        boolean isEmailExist = employeeRepository.existsByEmail(email);
        if (isEmailExist) {
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
            else {
                result.setMessage(CustomerSignUpConst.NULL_DATA);
                result.setStatus(ServiceResult.Status.FAILED);
            }
        }
        return result;
    }

    @Override
    public ServiceResult signInCustomer(String email, String password) {
        ServiceResult result = new ServiceResult();
        boolean isEmailExist = employeeRepository.existsByEmailAndDeleted(email, true);
        if (!isEmailExist) {
            result.setMessage(CustomerSignInConst.EMAIL_NOT_FOUND);
            result.setStatus(ServiceResult.Status.FAILED);
        } else {
                EmployeeEntity employee = employeeRepository.findByEmail(email).orElse(null);
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

    @Override
    public ServiceResult getCustomerById(int id) {
        ServiceResult result = new ServiceResult();
        boolean isCustomerExist = employeeRepository.existsByIdAndRoleName(id, RoleName.ROLE_CUSTOMER);
        if (isCustomerExist) {
            EmployeeEntity employee = employeeRepository.findById(id);
            CustomerGetInfoResponse response = new CustomerGetInfoResponse(
                    employee.getId(),
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getImageURL());
            result.setData(response);
            result.setMessage("ádas");
        } else {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage("ádasád");
        }
        return result;
    }

    @Override
    public ServiceResult updateInfo(int id, String firstName, String lastName, String password, String imageURL) {
        ServiceResult result = new ServiceResult();
        boolean isUserExist = employeeRepository.existsByIdAndRoleNameOrRoleName(id, RoleName.ROLE_CUSTOMER, RoleName.ROLE_SHOP);
        if (isUserExist) {
            if (firstName != null && lastName != null && password != null) {
                    EmployeeEntity employee = employeeRepository.findById(id);
                    employee.setFirstName(firstName);
                    employee.setLastName(lastName);
                    employee.setPassword(encoder.encode(password));
                    employee.setImageURL(imageURL);
                    employeeRepository.save(employee);
                    CustomerUpdateInfoResponse response = new CustomerUpdateInfoResponse(employee.getId(),
                            employee.getFirstName(),
                            employee.getLastName(),
                            employee.getImageURL());
                    result.setMessage("Update info successfully");
                    result.setData(response);


            } else {
                result.setMessage("Fields cannot be empty");
                result.setStatus(ServiceResult.Status.FAILED);
            }
        } else {
            result.setMessage("Customer not found");
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }
}
