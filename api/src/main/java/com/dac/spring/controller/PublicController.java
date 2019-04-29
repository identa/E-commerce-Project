package com.dac.spring.controller;

import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.req.AdminPaginateCategoryRequest;
import com.dac.spring.model.req.CustomerGetInfoRequest;
import com.dac.spring.model.req.CustomerSearchProductRequest;
import com.dac.spring.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("wishlist/add/{pid}/{uid}")
    public ResponseEntity<ServiceResult> addToWishlist(@PathVariable int pid, @PathVariable int uid){
        return new ResponseEntity<>(customerService.addToWishlist(pid, uid), HttpStatus.OK);
    }

    @DeleteMapping("wishlist/{id}")
    public ResponseEntity<ServiceResult> deleteWishlist(@PathVariable int id){
        return new ResponseEntity<>(customerService.deleteWishlist(id), HttpStatus.OK);
    }
}
