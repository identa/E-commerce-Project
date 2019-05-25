package com.dac.spring.controller;

import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.req.*;
import com.dac.spring.model.req.EditInfoReq;
import com.dac.spring.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/public")
public class PublicController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/searchProduct")
    public ResponseEntity<ServiceResult> searchProduct(@RequestBody CustomerSearchProductRequest request){
        return new ResponseEntity<>(customerService.searchProduct(request.getName(),
                request.getPage(),
                request.getSize()), HttpStatus.OK);
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

    @PostMapping("/getProduct")
    public ResponseEntity<ServiceResult> getProduct(@RequestBody CustomerGetInfoRequest request){
        return new ResponseEntity<>(customerService.getProduct(request.getId()),HttpStatus.OK);
    }

    @GetMapping("/getMVProduct")
    public ResponseEntity<ServiceResult> getMostViewedProduct(){
        return new ResponseEntity<>(customerService.getMostViewedProduct(),HttpStatus.OK);
    }

    @GetMapping("/getMVProduct/all")
    public ResponseEntity<ServiceResult> getMostViewedProductAll(){
        return new ResponseEntity<>(customerService.getMostViewedProductAll(),HttpStatus.OK);
    }

    @GetMapping("/getMOProduct")
    public ResponseEntity<ServiceResult> getMostOrderedProduct(){
        return new ResponseEntity<>(customerService.getMostOrderedProduct(),HttpStatus.OK);
    }

    @GetMapping("/getMOProduct/all")
    public ResponseEntity<ServiceResult> getMostOrderedProductAll(){
        return new ResponseEntity<>(customerService.getMostOrderedProductAll(),HttpStatus.OK);
    }

    @GetMapping("product/{pid}/{uid}")
    public ResponseEntity<ServiceResult> getCart(@PathVariable int pid, @PathVariable int uid){
        return new ResponseEntity<>(customerService.getProductDetail(pid, uid), HttpStatus.OK);
    }

    @GetMapping("cart/add/{pid}/{uid}")
    public ResponseEntity<ServiceResult> addToCart(@PathVariable int pid, @PathVariable int uid){
        return new ResponseEntity<>(customerService.addToCart(pid, uid), HttpStatus.OK);
    }

    @GetMapping("cart/get/{id}")
    public ResponseEntity<ServiceResult> getCart(@PathVariable int id){
        return new ResponseEntity<>(customerService.getCart(id), HttpStatus.OK);
    }

    @DeleteMapping("cart/delete/{id}")
    public ResponseEntity<ServiceResult> deleteCart(@PathVariable int id){
        return new ResponseEntity<>(customerService.deleteCart(id), HttpStatus.OK);
    }

    @PutMapping("cart/edit/{id}/{qty}")
    public ResponseEntity<ServiceResult> editCart(@PathVariable int id, @PathVariable int qty){
        return new ResponseEntity<>(customerService.editCart(id, qty), HttpStatus.OK);
    }

    @GetMapping("wishlist/{id}")
    public ResponseEntity<ServiceResult> getWishlist(@PathVariable int id){
        return new ResponseEntity<>(customerService.getWishlist(id), HttpStatus.OK);
    }

    @PostMapping("wishlist/{pid}/{uid}")
    public ResponseEntity<ServiceResult> addToWishlist(@PathVariable int pid, @PathVariable int uid){
        return new ResponseEntity<>(customerService.addToWishlist(pid, uid), HttpStatus.OK);
    }

    @DeleteMapping("wishlist/{pid}/{uid}")
    public ResponseEntity<ServiceResult> deleteWishlist(@PathVariable int pid, @PathVariable int uid){
        return new ResponseEntity<>(customerService.deleteWishlist(pid, uid), HttpStatus.OK);
    }

    @GetMapping("order/{id}")
    public ResponseEntity<ServiceResult> getOrder(@PathVariable int id){
        return new ResponseEntity<>(customerService.getOrder(id), HttpStatus.OK);
    }

    @PostMapping("order/{id}")
    public ResponseEntity<ServiceResult> addOrder(@PathVariable int id, @RequestBody AddOrderReq request){
        return new ResponseEntity<>(customerService.addOrder(id, request.getOrders()), HttpStatus.OK);
    }

    @GetMapping("product")
    public ResponseEntity<ServiceResult> search(@RequestParam(value = "search") String query){
        return new ResponseEntity<>(customerService.search(query), HttpStatus.OK);
    }

    @GetMapping("cat")
    public ResponseEntity<ServiceResult> getCat(){
        return new ResponseEntity<>(customerService.getCat(), HttpStatus.OK);
    }

    @GetMapping("cat/{id}")
    public ResponseEntity<ServiceResult> showCat(@PathVariable int id){
        return new ResponseEntity<>(customerService.showCat(id), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<ServiceResult> signIn(@Valid @RequestBody CustomerSignInRequest request) {
        return new ResponseEntity<>(customerService.signInCustomer(request.getEmail(),
                request.getPassword()), HttpStatus.OK);
    }

    @GetMapping("address/{id}")
    public ResponseEntity<ServiceResult> getAddress(@PathVariable int id){
        return new ResponseEntity<>(customerService.getAddress(id), HttpStatus.OK);
    }

    @PostMapping("address/{id}")
    public ResponseEntity<ServiceResult> addAddress(@PathVariable int id, @RequestBody AddressRequest request){
        return new ResponseEntity<>(customerService.addAddress(id, request), HttpStatus.OK);
    }

    @PutMapping("address/{id}")
    public ResponseEntity<ServiceResult> editAddress(@PathVariable int id, @RequestBody AddressRequest request){
        return new ResponseEntity<>(customerService.editAddress(id, request), HttpStatus.OK);
    }

    @GetMapping("order/detail/{id}")
    public ResponseEntity<ServiceResult> getOrderDetails(@PathVariable int id){
        return new ResponseEntity<>(customerService.getOrderDetails(id), HttpStatus.OK);
    }

    @GetMapping("mail")
    public ResponseEntity<ServiceResult> sendMail(){
        return new ResponseEntity<>(customerService.sendMail(), HttpStatus.OK);
    }

    @GetMapping("verify")
    public ResponseEntity<ServiceResult> verifyAccount(@RequestParam String email, @RequestParam String code){
        return new ResponseEntity<>(customerService.verify(email, code), HttpStatus.OK);
    }

    @PostMapping("sort")
    public ResponseEntity<ServiceResult> sort(@RequestBody SortRequest request){
        return new ResponseEntity<>(customerService.getSorted(request.getMin(),
                request.getMax(),
                request.getNames(),
                request.getOrderBy()), HttpStatus.OK);
    }

    @GetMapping("rating/{pid}/{uid}")
    public ResponseEntity<ServiceResult> getRating(@PathVariable int pid, @PathVariable int uid){
        return new ResponseEntity<>(customerService.getRating(pid, uid), HttpStatus.OK);
    }

    @PostMapping("info/{id}")
    public ResponseEntity<ServiceResult> editInfo(@PathVariable int id, @RequestBody EditInfoReq request){
        return new ResponseEntity<>(customerService.editInfo(id,
                request.getFirstName(),
                request.getLastName(),
                request.getPhoto()), HttpStatus.OK);
    }

    @PostMapping("pass/{id}")
    public ResponseEntity<ServiceResult> editPassword(@PathVariable int id, @RequestBody EditPassReq request){
        return new ResponseEntity<>(customerService.editPassword(id,
                request.getOldPass(),
                request.getNewPass()), HttpStatus.OK);
    }
}