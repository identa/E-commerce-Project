package com.dac.spring.controller;

import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.req.CustomerSignInRequest;
import com.dac.spring.model.req.CustomerSignUpRequest;
import com.dac.spring.service.CustomerService;
import com.dac.spring.utils.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<ServiceResult> customerSignUp(@Valid @RequestBody CustomerSignUpRequest request) {
        return new ResponseEntity<>(customerService.signUpCustomer(request.getEmail(),
                request.getPassword(),
                request.getFirstName(),
                request.getLastName()), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<ServiceResult> customerSignIn(@Valid @RequestBody CustomerSignInRequest request) {
        return new ResponseEntity<>(customerService.signInCustomer(request.getEmail(),
                request.getPassword()), HttpStatus.OK);
    }
}

