package com.dac.spring.controller;

import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.req.*;
import com.dac.spring.service.CustomerService;
import com.dac.spring.utils.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer")
public class CustomerController extends HttpServlet {
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

    @DeleteMapping("/signout")
    public ResponseEntity<ServiceResult> signOut(HttpServletRequest request) {
        return new ResponseEntity<>(customerService.signOut(request), HttpStatus.OK);
    }

    @GetMapping("/getById")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ServiceResult> getInfoById(HttpServletRequest request) {
        return new ResponseEntity<>(customerService.getInfo(request), HttpStatus.OK);
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

    @PostMapping("/getProductByCat")
    public ResponseEntity<ServiceResult> getProductByCat(@RequestBody CustomerGetProductByCatRequest request){
        return new ResponseEntity<>(customerService.paginateProductByCat(request.getId(),
                request.getPage(),
                request.getSize()),HttpStatus.OK);
    }

    @GetMapping("/getRole")
    public ResponseEntity<ServiceResult> getRole(HttpServletRequest request){
        return new ResponseEntity<>(customerService.returnRole(request),HttpStatus.OK);
    }

    @PostMapping("/createOrder")
    public ResponseEntity<ServiceResult> createOrder(@RequestBody CustomerCreateOrderRequest request){
        return new ResponseEntity<>(customerService.createOrder(request.getCustomerID(),request.getOrderDetailRequests()),HttpStatus.OK);
    }

    @DeleteMapping("/deleteOrder")
    public ResponseEntity<ServiceResult> deleteOrder(@RequestBody CustomerDeleteOrderRequest request){
        return new ResponseEntity<>(customerService.deleteOrder(request.getId()),HttpStatus.OK);
    }

    @PostMapping("/searchProduct")
    public ResponseEntity<ServiceResult> searchProduct(@RequestBody CustomerSearchProductRequest request){
        return new ResponseEntity<>(customerService.searchProduct(request.getName(),
                request.getPage(),
                request.getSize()),HttpStatus.OK);
    }

    @PostMapping("/paginateProduct")
    public ResponseEntity<ServiceResult> paginateProduct(@RequestBody AdminPaginateCategoryRequest request){
        return new ResponseEntity<>(customerService.paginateProduct(request.getPage(),
                request.getSize()),HttpStatus.OK);
    }

    @GetMapping("/getCampaign")
    public ResponseEntity<ServiceResult> getCampaign(){
        return new ResponseEntity<>(customerService.getCampaign(),HttpStatus.OK);
    }
}