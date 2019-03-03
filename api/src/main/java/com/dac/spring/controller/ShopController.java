package com.dac.spring.controller;

import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.req.ShopCreateProductRequest;
import com.dac.spring.model.req.ShopGetInfoRequest;
import com.dac.spring.model.req.ShopGetProductRequest;
import com.dac.spring.model.req.ShopUpdateInfoRequest;
import com.dac.spring.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/shop")
public class ShopController {

    @Autowired
    ShopService shopService;

    @PostMapping("/getInfoById")
    @PreAuthorize("hasRole('SHOP')")
    public ResponseEntity<ServiceResult> getInfoById(@Valid @RequestBody ShopGetInfoRequest request) {
        return new ResponseEntity<>(shopService.getInfoById(request.getId()), HttpStatus.OK);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('SHOP')")
    public ResponseEntity<ServiceResult> updateInfo(@Valid @RequestBody ShopUpdateInfoRequest request) {
        return new ResponseEntity<>(shopService.updateInfo(request.getId(),
                request.getFirstName(),
                request.getLastName(),
                request.getPassword(),
                request.getImageURL()),HttpStatus.OK);
    }

    @PostMapping("/paginateProduct")
    @PreAuthorize("hasRole('SHOP')")
    public ResponseEntity<ServiceResult> paginateProduct(@Valid @RequestBody ShopGetProductRequest request) {
        return new ResponseEntity<>(shopService.paginateProductById(request.getId(),
                request.getPage(),
                request.getSize()),HttpStatus.OK);
    }

    @PostMapping("/createProduct")
    @PreAuthorize("hasRole('SHOP')")
    public ResponseEntity<ServiceResult> createProduct(@Valid @RequestBody ShopCreateProductRequest request) {
        return new ResponseEntity<>(shopService.createProduct(request.getName(),
                request.getStatus(),
                request.getDescription(),
                request.getQuantity(),
                request.getOriginalPrice(),
                request.getDiscount(),
                request.getProductImageURL(),
                request.getCategoryID(),
                request.getShopID()),HttpStatus.OK);
    }

    @PostMapping("/updateProduct")
    @PreAuthorize("hasRole('SHOP')")
    public ResponseEntity<ServiceResult> updateProduct(@Valid @RequestBody ShopUpdateInfoRequest request) {
        return new ResponseEntity<>(shopService.updateInfo(request.getId(),
                request.getFirstName(),
                request.getLastName(),
                request.getPassword(),
                request.getImageURL()),HttpStatus.OK);
    }

    @PostMapping("/deleteProduct")
    @PreAuthorize("hasRole('SHOP')")
    public ResponseEntity<ServiceResult> deleteProduct(@Valid @RequestBody ShopUpdateInfoRequest request) {
        return new ResponseEntity<>(shopService.updateInfo(request.getId(),
                request.getFirstName(),
                request.getLastName(),
                request.getPassword(),
                request.getImageURL()),HttpStatus.OK);
    }
}
