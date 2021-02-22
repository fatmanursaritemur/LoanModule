package com.turkcell.loanmodule.business.abstracts;

import com.turkcell.loanmodule.entities.concretes.Credit;
import com.turkcell.loanmodule.entities.concretes.Customer;

public interface IPropertyService {
 Customer getCurrentCustomer() throws Exception;

 Credit getCurrentCredit() throws Exception;

 void  updateCurrentCustomerAndCredit(Credit credit);

  void deleteCurrentCustomerAndCredit();
}
