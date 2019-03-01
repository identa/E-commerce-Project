package com.dac.spring.service.impl;

import com.dac.spring.entity.EmployeeEntity;
import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.enums.RoleName;
import com.dac.spring.model.resp.ShopGetInfoResponse;
import com.dac.spring.model.resp.ShopUpdateInfoResponse;
import com.dac.spring.repository.EmployeeRepository;
import com.dac.spring.repository.RoleRepository;
import com.dac.spring.repository.StatusRepository;
import com.dac.spring.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public ServiceResult getInfoById(int id) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity shop = employeeRepository.findByIdAndDeletedAndRoleName(id, false,
                RoleName.ROLE_SHOP).orElse(null);
        if (shop != null) {
            ShopGetInfoResponse response = new ShopGetInfoResponse(
                    shop.getId(),
                    shop.getFirstName(),
                    shop.getLastName(),
                    shop.getImageURL());
            result.setData(response);
            result.setMessage("Get info successfully");
        } else {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage("Shop not found");
        }
        return result;
    }

    @Override
    public ServiceResult updateInfo(int id, String firstName, String lastName, String password, String imageURL) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity shop = employeeRepository.findByIdAndDeletedAndRoleName(id, false,
                RoleName.ROLE_SHOP).orElse(null);
        if (shop != null) {
            if (firstName != null && lastName != null && password != null) {
                shop.setFirstName(firstName);
                shop.setLastName(lastName);
                shop.setPassword(encoder.encode(password));
                shop.setImageURL(imageURL);
                employeeRepository.save(shop);
                ShopUpdateInfoResponse response = new ShopUpdateInfoResponse(shop.getId(),
                        shop.getFirstName(),
                        shop.getLastName(),
                        shop.getImageURL());
                result.setMessage("Update info successfully");
                result.setData(response);
            } else {
                result.setMessage("Fields cannot be empty");
                result.setStatus(ServiceResult.Status.FAILED);
            }
        } else {
            result.setMessage("Shop not found");
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }
}
