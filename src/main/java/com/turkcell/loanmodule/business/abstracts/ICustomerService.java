package com.turkcell.loanmodule.business.abstracts;

import com.turkcell.loanmodule.entities.concretes.Customer;

public interface ICustomerService {
 Customer save(Customer customer);

  Customer findById(Long id) throws Exception;
}
