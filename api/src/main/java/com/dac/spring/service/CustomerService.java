package com.dac.spring.service;

import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.req.CustomerCreateOrderDetailRequest;

import java.util.List;

public interface CustomerService {
    ServiceResult signUpCustomer(String email, String password, String firstName, String lastName);
    ServiceResult signIn(String email, String password);
    ServiceResult signOut(String token);
    ServiceResult getInfo(String token);
    ServiceResult updateInfo(int id, String firstName, String lastName, String password, String imageURL);
    ServiceResult getCategoryTree();
    ServiceResult getAllCategory();
    ServiceResult paginateProductByCat(int id, int page, int size);
    ServiceResult getProduct(int id);
    ServiceResult returnRole(String token);
    ServiceResult createOrder(int customerID, List<CustomerCreateOrderDetailRequest> orderDetailRequests);
    ServiceResult deleteOrder(int id);
    ServiceResult searchProduct(String name, int page, int size);
    ServiceResult paginateProduct(int page, int size);
    ServiceResult getCampaign();
    ServiceResult getMostViewedProduct();
    ServiceResult getMostOrderedProduct();
    ServiceResult getProductDetail(int pid, int uid);
    ServiceResult addToCart(int pid, int uid);
    ServiceResult getCart(int id);
    ServiceResult deleteCart(int id);
}
