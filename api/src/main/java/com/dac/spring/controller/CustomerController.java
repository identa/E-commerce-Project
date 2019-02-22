package com.dac.spring.controller;

import com.dac.spring.model.CustomerSignInRequest;
import com.dac.spring.model.CustomerSignUpRequest;
import com.dac.spring.model.ServiceResult;
import com.dac.spring.response.JwtResponse;
import com.dac.spring.security.jwt.JwtProvider;
import com.dac.spring.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*",maxAge = 3600)
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

    @PostMapping("/signin")
    public ResponseEntity<ServiceResult> customerSignIn(@Valid @RequestBody CustomerSignInRequest request) {
        return new ResponseEntity<>(customerService.signInCustomer(request.getEmail(),
                request.getPassword()), HttpStatus.OK);
    }

//    @PostMapping("/signin")
//    public ResponseEntity<?> authenticateUser(@Valid @RequestBody CustomerSignInRequest request) {
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getEmail(),
//                        request.getPassword()
//                )
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String jwt = jwtProvider.generateJwtToken(authentication);
//        return ResponseEntity.ok(new JwtResponse(jwt));
//    }

    @PostMapping("/signup")
    public ResponseEntity<ServiceResult> customerSignUp(@Valid @RequestBody CustomerSignUpRequest request) {
        return new ResponseEntity<>(customerService.signUpCustomer(request.getEmail(),
                request.getPassword(),
                request.getFirstName(),
                request.getLastName()), HttpStatus.OK);
    }
}
