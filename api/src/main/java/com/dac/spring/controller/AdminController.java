package com.dac.spring.controller;

import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.enums.RoleName;
import com.dac.spring.model.enums.StatusName;
import com.dac.spring.model.req.AdminGetCustomerByIdRequest;
import com.dac.spring.model.req.AdminUpdateCustomerRequest;
import com.dac.spring.model.req.CustomerSignUpRequest;
import com.dac.spring.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @GetMapping("/all")
    public ResponseEntity<ServiceResult> getAllEmployee() {
        return new ResponseEntity<>(adminService.getAllCustomer(), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<ServiceResult> signUpAdmin(@RequestBody CustomerSignUpRequest request) {
        return new ResponseEntity<>(adminService.signUpAdmin(request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword()),
                HttpStatus.OK);
    }

    @GetMapping("/getById")
    public ResponseEntity<ServiceResult> getCustomerById(@RequestBody AdminGetCustomerByIdRequest request) {
        return new ResponseEntity<>(adminService.getCustomerById(request.getId()), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ServiceResult> updateCustomer(@RequestBody AdminUpdateCustomerRequest request) {
        return new ResponseEntity<>(adminService.updateCustomer(request.getId(),
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                StatusName.valueOf(request.getStatus()),
                RoleName.valueOf(request.getRole())),
                HttpStatus.OK);
    }

    @PutMapping("/delete")
    public ResponseEntity<ServiceResult> deleteCustomer(@RequestBody AdminGetCustomerByIdRequest request) {
        return new ResponseEntity<>(adminService.deleteCustomerById(request.getId()), HttpStatus.OK);
    }
}
