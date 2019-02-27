package com.dac.spring.service;

import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.enums.RoleName;
import com.dac.spring.model.enums.StatusName;

public interface AdminService {
    ServiceResult getAllCustomer();
    ServiceResult getCustomerById(int id);
    ServiceResult updateUser(int id, String firstName, String lastName, String password, StatusName statusName, RoleName roleName);
    ServiceResult deleteUserById(int id);
    ServiceResult createEmployee(String firstName, String lastName, String email, String password, StatusName statusName, RoleName roleName);
    ServiceResult signupAdmin(String firstName, String lastName, String email, String password);
}
