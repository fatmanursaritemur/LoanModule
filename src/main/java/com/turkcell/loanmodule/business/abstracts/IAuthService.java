package com.turkcell.loanmodule.business.abstracts;

import com.turkcell.loanmodule.security.payload.request.LoginRequest;
import com.turkcell.loanmodule.security.payload.request.SignupRequest;
import com.turkcell.loanmodule.security.services.UserDetailsImpl;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;


public interface IAuthService {

  ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest);

  void setCurrentUser(List<String> roles, UserDetailsImpl userDetails);

  ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest);

  void logout();

  void deleteAllProperties();

}
