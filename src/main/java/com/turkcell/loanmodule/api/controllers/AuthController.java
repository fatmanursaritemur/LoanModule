package com.turkcell.loanmodule.api.controllers;

import com.turkcell.loanmodule.business.abstracts.IAuthService;

import com.turkcell.loanmodule.entities.concretes.Customer;
import com.turkcell.loanmodule.payload.request.LoginRequest;
import com.turkcell.loanmodule.payload.request.SignupRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
  IAuthService authService;


	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		return authService.authenticateUser(loginRequest);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		return authService.registerUser(signUpRequest);
	}

	@GetMapping("/test")
	public Customer test() throws Exception {
		return authService.test();
	}

	@GetMapping("/my-logout")
	public void logout() {
		 authService.deleteCurrentCustomerAndCredit();
	}


}