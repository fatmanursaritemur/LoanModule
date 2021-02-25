package com.turkcell.loanmodule.business.abstracts;

import com.turkcell.loanmodule.entities.concretes.Credit;
import com.turkcell.loanmodule.entities.concretes.Customer;
import com.turkcell.loanmodule.entities.concretes.Employee;
import com.turkcell.loanmodule.entities.concretes.Property;

public interface IPropertyService {

  Customer getCurrentCustomer() throws Exception;

  Employee getCurrentEmployee() throws Exception;

  Credit getCurrentCredit() throws Exception;

  Property setProperty(String propertyName, Long id);

  void updateCurrentCustomerAndCredit(Credit credit);

  void deleteAllProperties();
}
