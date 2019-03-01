package com.dac.spring.service;

import com.dac.spring.model.ServiceResult;

public interface ShopService {
    ServiceResult getInfoById(int id);
    ServiceResult updateInfo(int id, String firstName, String lastName, String password, String imageURL);
}
