package com.dac.spring.controller;

import com.dac.spring.model.CustomerSignInRequest;
import com.dac.spring.model.CustomerSignUpRequest;
import com.dac.spring.model.ServiceResult;
import com.dac.spring.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://192.168.1.147:3000")
@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/signin")
    public ResponseEntity<ServiceResult> customerSignIn(@RequestBody CustomerSignInRequest request) {
        return new ResponseEntity<>(customerService.signInCustomer(request.getEmail(),
                request.getPassword()), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<ServiceResult> customerSignUp(@RequestBody CustomerSignUpRequest request) {
        return new ResponseEntity<>(customerService.signUpCustomer(request.getEmail(),
                request.getPassword(),
                request.getFirstName(),
                request.getLastName()), HttpStatus.OK);
    }
}
