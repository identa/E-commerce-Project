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
            if (employeeRepository.existsByEmailAndDeleted(email, true)) {
                result.setStatus(ServiceResult.Status.FAILED);
                result.setMessage("This email cannot be used to sign up");
            } else {
                result.setStatus(ServiceResult.Status.FAILED);
                result.setMessage(CustomerSignUpConst.EMAIL_EXIST);
            }
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
            } else {
                result.setMessage(CustomerSignUpConst.NULL_DATA);
                result.setStatus(ServiceResult.Status.FAILED);
            }
        }
        return result;
    }

    @Override
    public ServiceResult signIn(String email, String password) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity customer = employeeRepository.findByEmailAndDeleted(email, false).orElse(null);
        if (customer != null) {
            boolean isPasswordChecked = encoder.matches(password, customer.getPassword());
            if (isPasswordChecked) {
                String jwt = authenticationWithJwt(email, password);
                CustomerSignInSignUpResponse response = new CustomerSignInSignUpResponse(customer.getId(),
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getRole().getName().name(),
                        jwt);
                result.setMessage(CustomerSignInConst.SUCCESS);
                result.setData(response);
            } else {
                result.setStatus(ServiceResult.Status.FAILED);
                result.setMessage(CustomerSignInConst.EMAIL_PASSWORD_WRONG_FORMAT);
            }

        } else {
            result.setMessage(CustomerSignInConst.EMAIL_NOT_FOUND);
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    @Override
    public ServiceResult getInfoById(int id) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity customer = employeeRepository.findByIdAndDeletedAndRoleName(id, false,
                RoleName.ROLE_CUSTOMER).orElse(null);
        if (customer != null) {
            CustomerGetInfoResponse response = new CustomerGetInfoResponse(
                    customer.getId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getImageURL());
            result.setData(response);
            result.setMessage("Get info successfully");
        } else {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage("Customer not found");
        }
        return result;
    }

    @Override
    public ServiceResult updateInfo(int id, String firstName, String lastName, String password, String imageURL) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity customer = employeeRepository.findByIdAndDeletedAndRoleName(id, false,
                RoleName.ROLE_CUSTOMER).orElse(null);
        if (customer != null) {
            if (firstName != null && lastName != null && password != null) {
                customer.setFirstName(firstName);
                customer.setLastName(lastName);
                customer.setPassword(encoder.encode(password));
                customer.setImageURL(imageURL);
                employeeRepository.save(customer);
                CustomerUpdateInfoResponse response = new CustomerUpdateInfoResponse(customer.getId(),
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getImageURL());
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
