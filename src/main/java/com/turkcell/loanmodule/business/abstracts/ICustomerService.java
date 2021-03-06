package com.turkcell.loanmodule.business.abstracts;

import com.turkcell.loanmodule.entities.concretes.Customer;
import com.turkcell.loanmodule.entities.dtos.CreditApplianceResultDto;
import com.turkcell.loanmodule.entities.enums.CreditCustomerStatus;
import java.util.List;

public interface ICustomerService {

  Customer save(Customer customer);

  Customer findById(Long id) throws Exception;

  Integer setCreditScore(Customer customer);

  Integer getPhonePillScoreOfCustomer(Customer customer);

  void evaluateModifiedCredit(Customer customer, CreditCustomerStatus creditCustomerStatus);

  CreditApplianceResultDto getLastRejectCredit(Customer customer);

  List<Customer> getCustomersRegisteredToday();

  List<Customer> findAll();
}
