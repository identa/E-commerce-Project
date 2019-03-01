package com.dac.spring.controller;

import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.req.CustomerGetInfoRequest;
import com.dac.spring.model.req.CustomerSignInRequest;
import com.dac.spring.model.req.CustomerSignUpRequest;
import com.dac.spring.model.req.CustomerUpdateInfoRequest;
import com.dac.spring.service.CustomerService;
import com.dac.spring.utils.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public ResponseEntity<ServiceResult> signIn(@Valid @RequestBody CustomerSignInRequest request) {
        return new ResponseEntity<>(customerService.signIn(request.getEmail(),
                request.getPassword()), HttpStatus.OK);
    }

    @PostMapping("/getById")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ServiceResult> getInfoById(@Valid @RequestBody CustomerGetInfoRequest request) {
        return new ResponseEntity<>(customerService.getInfoById(request.getId()), HttpStatus.OK);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ServiceResult> updateInfo(@Valid @RequestBody CustomerUpdateInfoRequest request) {
        return new ResponseEntity<>(customerService.updateInfo(request.getId(),
                request.getFirstName(),
                request.getLastName(),
                request.getPassword(),
                request.getImageURL()),HttpStatus.OK);
    }

    @GetMapping("/getCategoryTree")
    public ResponseEntity<ServiceResult> getCategoryTree(){
        return new ResponseEntity<>(customerService.getCategoryTree(),HttpStatus.OK);
    }

    @GetMapping("/getAllCategory")
    public ResponseEntity<ServiceResult> getAllCategory(){
        return new ResponseEntity<>(customerService.getAllCategory(),HttpStatus.OK);
    }
}