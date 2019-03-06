package com.dac.spring.controller;

import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.req.*;
import com.dac.spring.service.AdminService;
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
        return new ResponseEntity<>(adminService.createCategory(request.getName(), request.getParentName()),HttpStatus.OK);
    }

    @PutMapping("/updateCategory")
    public ResponseEntity<ServiceResult> createCategory(@Valid @RequestBody AdminUpdateCategoryRequest request){
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

    @PostMapping("/createProduct")
    public ResponseEntity<ServiceResult> createProduct(@Valid @RequestBody AdminCreateProductRequest request) {
        return new ResponseEntity<>(adminService.createProduct(request),HttpStatus.OK);
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
}
