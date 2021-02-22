package com.turkcell.loanmodule.business.abstracts;

import com.turkcell.loanmodule.entities.concretes.Customer;
import com.turkcell.loanmodule.payload.request.LoginRequest;
import com.turkcell.loanmodule.payload.request.SignupRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;


public interface IAuthService {
  ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest);

  ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest);

  void deleteCurrentCustomerAndCredit();

  Customer test() throws Exception;
}
