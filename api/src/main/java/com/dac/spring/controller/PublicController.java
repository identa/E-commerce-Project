package com.dac.spring.controller;

import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.req.AdminPaginateCategoryRequest;
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
}
