package com.dac.spring.controller;

import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.req.*;
import com.dac.spring.service.AdminService;
import com.dac.spring.service.CustomerService;
import com.dac.spring.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    AdminService adminService;

    @Autowired
    CustomerService customerService;

    @Autowired
    ShopService shopService;

    @GetMapping("/all")
    public ResponseEntity<ServiceResult> getAllEmployee() {
        return new ResponseEntity<>(adminService.getAllCustomer(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ServiceResult> createEmployee(@Valid @RequestBody AdminCreateUserRequest request) {
        return new ResponseEntity<>(adminService.createEmployee(request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                request.getImageURL(),
                request.getStatus(),
                request.getRole()),
                HttpStatus.OK);
    }

    @GetMapping("/getById")
    public ResponseEntity<ServiceResult> getCustomerById(@Valid @RequestBody AdminGetCustomerByIdRequest request) {
        return new ResponseEntity<>(adminService.getCustomerById(request.getId()), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ServiceResult> updateCustomer(@Valid @RequestBody AdminUpdateCustomerRequest request) {
        return new ResponseEntity<>(adminService.updateUser(request.getId(),
                request.getFirstName(),
                request.getLastName(),
                request.getPassword(),
                request.getImageURL(),
                request.getStatus(),
                request.getRole()),
                HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ServiceResult> deleteCustomer(@Valid @RequestBody AdminDeleteCustomerRequest request) {
        return new ResponseEntity<>(adminService.deleteUserById(request.getId()), HttpStatus.OK);
    }

    @PostMapping("/getByPageAndSize")
    public ResponseEntity<ServiceResult> paginateUser(@Valid @RequestBody AdminPaginateUserRequest request) {
        return new ResponseEntity<>(adminService.paginateUser(request.getPage(),
                request.getSize()), HttpStatus.OK);
    }

    @PostMapping("/createCategory")
    public ResponseEntity<ServiceResult> createCategory(@Valid @RequestBody AdminCreateCategoryRequest request){
        return new ResponseEntity<>(adminService.createCategory(request.getName(), request.getParentID()),HttpStatus.OK);
    }

    @PutMapping("/updateCategory")
    public ResponseEntity<ServiceResult> updateCategory(@Valid @RequestBody AdminUpdateCategoryRequest request){
        return new ResponseEntity<>(adminService.updateCategory(request.getId(), request.getName(), request.getParentName()),HttpStatus.OK);
    }

    @PostMapping("/paginateCategory")
    public ResponseEntity<ServiceResult> paginateCategory(@Valid @RequestBody AdminPaginateCategoryRequest request){
        return new ResponseEntity<>(adminService.paginateCategory(request.getPage(), request.getSize()),HttpStatus.OK);
    }

    @DeleteMapping("/deleteCategory")
    public ResponseEntity<ServiceResult> deleteCategory(@Valid @RequestBody AdminDeleteCategoryRequest request){
        return new ResponseEntity<>(adminService.deleteCategory(request.getId()),HttpStatus.OK);
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<ServiceResult> updateProduct(@Valid @RequestBody ShopUpdateProductRequest request) {
        return new ResponseEntity<>(adminService.updateProduct(request),HttpStatus.OK);
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity<ServiceResult> deleteProduct(@Valid @RequestBody ShopDeleteProductRequest request) {
        return new ResponseEntity<>(adminService.deleteProduct(request.getId()),HttpStatus.OK);
    }

    @PostMapping("/paginateProduct")
    public ResponseEntity<ServiceResult> paginateProduct(@Valid @RequestBody AdminPaginateProductRequest request) {
        return new ResponseEntity<>(adminService.paginateProduct(request.getPage(),
                request.getSize()),HttpStatus.OK);
    }

    @PostMapping("/paginateOrder")
    public ResponseEntity<ServiceResult> paginateOrder(@RequestBody AdminPaginateProductRequest request){
        return new ResponseEntity<>(adminService.paginateOrder(request.getPage(),request.getSize()),HttpStatus.OK);
    }

    @PostMapping("/createOrder")
    public ResponseEntity<ServiceResult> createOrder(@RequestBody CustomerCreateOrderRequest request){
        return new ResponseEntity<>(customerService.createOrder(request.getCustomerID(),request.getOrderDetailRequests()),HttpStatus.OK);
    }

    @PutMapping("/updateOrder")
    public ResponseEntity<ServiceResult> updateOrder(@Valid @RequestBody ShopUpdateOrderRequest request) {
        return new ResponseEntity<>(shopService.updateOrder(request.getId(),
                request.getStatus()),HttpStatus.OK);
    }

    @DeleteMapping("/deleteOrder")
    public ResponseEntity<ServiceResult> deleteOrder(@Valid @RequestBody ShopDeleteProductRequest request) {
        return new ResponseEntity<>(adminService.deleteOrder(request.getId()),HttpStatus.OK);
    }

    @PostMapping("/paginateOrderDetail")
    public ResponseEntity<ServiceResult> paginateOrderDetail(@RequestBody AdminPaginateOrderDetailRequest request){
        return new ResponseEntity<>(adminService.paginateOrderDetail(request.getId(),
                request.getPage(),
                request.getSize()),HttpStatus.OK);
    }


    @PostMapping("/createOrderDetail")
    public ResponseEntity<ServiceResult> createOrderDetail(@Valid @RequestBody AdminCreateOrderDetailRequest request) {
        return new ResponseEntity<>(adminService.createOrderDetail(request.getId(),
                request.getQuantity(),
                request.getProductID(),
                request.getOrderID()),HttpStatus.OK);
    }

    @PutMapping("/updateOrderDetail")
    public ResponseEntity<ServiceResult> updateOrderDetail(@Valid @RequestBody ShopUpdateOrderDetailRequest request) {
        return new ResponseEntity<>(shopService.updateOrderDetail(request.getId(),
                request.getQuantity(),
                request.getProductID()),HttpStatus.OK);
    }

    @DeleteMapping("/deleteOrderDetail")
    public ResponseEntity<ServiceResult> deleteOrderDetail(@Valid @RequestBody ShopDeleteProductRequest request) {
        return new ResponseEntity<>(adminService.deleteOrderDetail(request.getId()),HttpStatus.OK);
    }

    @GetMapping("/getShop")
    public ResponseEntity<ServiceResult> getShop() {
        return new ResponseEntity<>(adminService.getAllShop(),HttpStatus.OK);
    }

    @PostMapping("/paginateCampaign")
    public ResponseEntity<ServiceResult> paginateCampaign(@RequestBody AdminPaginateUserRequest request){
        return new ResponseEntity<>(adminService.paginateCampaign(request.getPage(),
                request.getSize()),HttpStatus.OK);
    }

    @PostMapping("/createCampaign")
    public ResponseEntity<ServiceResult> createCampaign(@RequestBody AdminCreateCampaignRequest request){
        return new ResponseEntity<>(adminService.createCampaign(request),HttpStatus.OK);
    }

    @PutMapping("/updateCampaign")
    public ResponseEntity<ServiceResult> updateCampaign(@RequestBody AdminUpdateCampaignRequest request){
        return new ResponseEntity<>(adminService.updateCampaign(request),HttpStatus.OK);
    }

    @DeleteMapping("/deleteCampaign")
    public ResponseEntity<ServiceResult> deleteCampaign(@RequestBody AdminDeleteCategoryRequest request){
        return new ResponseEntity<>(adminService.deleteCampaign(request.getId()),HttpStatus.OK);
    }
}
