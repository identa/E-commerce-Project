package com.dac.spring.controller;

import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.req.CustomerSignUpRequest;
import com.dac.spring.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestAPIs {

	@Autowired
	AdminService adminService;

	@GetMapping("/api/test/user")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public String userAccess() {
		return ">>> User Contents!";
	}

	@PostMapping("/api/test/signup")
	public ResponseEntity<ServiceResult> signupAdmin(@RequestBody CustomerSignUpRequest request) {
		return new ResponseEntity<>(adminService.signupAdmin(request.getFirstName(),
				request.getLastName(),
				request.getEmail(),
				request.getPassword()),
				HttpStatus.OK);
	}
	
	@GetMapping("/api/test/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return ">>> Admin Contents";
	}
}