package com.dac.spring.service;

import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.req.ShopCreateProductRequest;
import com.dac.spring.model.req.ShopUpdateProductRequest;

public interface ShopService {
    ServiceResult getInfoById(int id);
    ServiceResult updateInfo(int id, String firstName, String lastName, String password, String imageURL);
    ServiceResult paginateProductById(int id, int page, int size);
    ServiceResult createProduct(ShopCreateProductRequest request);
    ServiceResult updateProduct(ShopUpdateProductRequest request);
    ServiceResult deleteProduct(int id);
}
