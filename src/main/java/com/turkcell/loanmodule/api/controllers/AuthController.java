package com.turkcell.loanmodule.api.controllers;

import com.turkcell.loanmodule.business.abstracts.IAuthService;

import com.turkcell.loanmodule.business.abstracts.IEmployeeService;
import com.turkcell.loanmodule.entities.concretes.Employee;
import com.turkcell.loanmodule.security.payload.request.LoginRequest;
import com.turkcell.loanmodule.security.payload.request.SignupRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

  @Autowired
  IEmployeeService employeeService;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    return authService.authenticateUser(loginRequest);
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    return authService.registerUser(signUpRequest);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/saveEmployee")
  public Employee saveEmployee(@Valid @RequestBody Employee employee) {
    return employeeService.save(employee);
  }


  @GetMapping("/logout")
  public void logout() {
    authService.deleteAllProperties();
  }


}