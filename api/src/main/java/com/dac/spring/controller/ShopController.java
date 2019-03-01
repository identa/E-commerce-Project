package com.dac.spring.controller;

import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.req.ShopGetInfoRequest;
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

    @PostMapping("/getById")
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
}
