package com.turkcell.loanmodule.business.concretes;

import com.turkcell.loanmodule.business.abstracts.IAuthService;
import com.turkcell.loanmodule.business.abstracts.ICustomerService;
import com.turkcell.loanmodule.dataAccess.CustomerRepository;
import com.turkcell.loanmodule.entities.concretes.Customer;
import com.turkcell.loanmodule.payload.request.SignupRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class CustomerManager implements ICustomerService {

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  IAuthService authService;

  @Autowired
  ModelMapper modelMapper;

  @Override
  public Customer save(Customer customer) {
    SignupRequest signupRequest = modelMapper.map(customer, SignupRequest.class);
    authService.registerUser(signupRequest);
    return customerRepository.save(customer);
  }

  @Override
  public Customer findById(Long id) throws Exception {
    return  customerRepository.findById(id).orElseThrow(Exception::new);
  }
}
