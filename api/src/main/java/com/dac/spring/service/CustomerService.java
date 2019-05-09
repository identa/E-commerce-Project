package com.dac.spring.service;

import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.req.AddOrderRequest;
import com.dac.spring.model.req.AddressRequest;
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
    ServiceResult getMostViewedProductAll();
    ServiceResult getMostOrderedProductAll();
    ServiceResult getProductDetail(int pid, int uid);
    ServiceResult addToCart(int pid, int uid);
    ServiceResult getCart(int id);
    ServiceResult deleteCart(int id);
    ServiceResult editCart(int id, int qty);
    ServiceResult getWishlist(int id);
    ServiceResult addToWishlist(int pid, int uid);
    ServiceResult deleteWishlist(int pid, int uid);
    ServiceResult getOrder(int id);
    ServiceResult addOrder(int id, List<AddOrderRequest> request);
    ServiceResult search(String query);
    ServiceResult getCat();
    ServiceResult showCat(int id);
    ServiceResult signInCustomer(String email, String password);
    ServiceResult getAddress(int id);
    ServiceResult addAddress(int id, AddressRequest request);
    ServiceResult editAddress(int id, AddressRequest request);
    ServiceResult getOrderDetails(int id);
}