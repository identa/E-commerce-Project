package com.dac.spring.service;

import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.enums.RoleName;
import com.dac.spring.model.enums.StatusName;

public interface AdminService {
    ServiceResult getAllCustomer();
    ServiceResult getCustomerById(int id);
    ServiceResult updateCustomer(int id, String firstName, String lastName, String password, StatusName statusName, RoleName roleName);
    ServiceResult deleteCustomerById(int id);
    ServiceResult createEmployee(String firstName, String lastName, String email, String password, RoleName roleName);
}
