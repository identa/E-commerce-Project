package com.dac.spring.service;

import com.dac.spring.model.ServiceResult;

public interface ShopService {
    ServiceResult getInfoById(int id);
    ServiceResult updateInfo(int id, String firstName, String lastName, String password, String imageURL);
    ServiceResult paginateProductById(int id, int page, int size);
    ServiceResult createProduct(String name, String status, String description, int quantity,
                                double originalPrice, int discount, String productImageURL, int categoryID, int shopID);
    ServiceResult updateProduct(int id, String name, String status, String description, int quantity,
                                double originalPrice, int discount, String productImageURL, int categoryID);

    ServiceResult deleteProduct(int id);
}
