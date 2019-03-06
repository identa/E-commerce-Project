package com.dac.spring.service;

import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.req.CustomerCreateOrderDetailRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CustomerService {
    ServiceResult signUpCustomer(String email, String password, String firstName, String lastName);
    ServiceResult signIn(String email, String password);
    ServiceResult signOut(HttpServletRequest request);
    ServiceResult getInfoById(int id);
    ServiceResult updateInfo(int id, String firstName, String lastName, String password, String imageURL);
    ServiceResult getCategoryTree();
    ServiceResult getAllCategory();
    ServiceResult paginateProductByCat(int id, int page, int size);
    ServiceResult returnRole(HttpServletRequest request);
    ServiceResult createOrder(int customerID, List<CustomerCreateOrderDetailRequest> orderDetailRequests);
    ServiceResult searchProduct(String name, int page, int size);
    ServiceResult paginateProduct(int page, int size);
}
