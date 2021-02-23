package com.turkcell.loanmodule.business.concretes;

import com.turkcell.loanmodule.business.abstracts.IAuthService;
import com.turkcell.loanmodule.business.abstracts.IBlacklistService;
import com.turkcell.loanmodule.business.abstracts.ICreditHistoryService;
import com.turkcell.loanmodule.business.abstracts.ICustomerService;
import com.turkcell.loanmodule.dataAccess.CustomerRepository;
import com.turkcell.loanmodule.dataAccess.PhoneBillRepository;
import com.turkcell.loanmodule.entities.concretes.Customer;
import com.turkcell.loanmodule.entities.enums.CreditCustomerStatus;
import com.turkcell.loanmodule.payload.request.SignupRequest;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class CustomerManager implements ICustomerService {

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  PhoneBillRepository phoneBillRepository;

  @Autowired
  IAuthService authService;

  @Autowired
  ICreditHistoryService creditHistoryService;

  @Autowired
  IBlacklistService blacklistService;

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
    return customerRepository.findById(id).orElseThrow(Exception::new);
  }

  @Override
  public Integer setCreditScore(Customer customer) {
    int totalNote = (creditHistoryService.getCreditScoreOfCustomer(customer) +
        blacklistService.getBlacklistScoreOfCustomer(customer) +
        getPhonePillScoreOfCustomer(customer))/10;
     customer.setCreditNote(1000+totalNote);
     return customerRepository.save(customer).getCreditNote();

  }

  @Override
  public Integer getPhonePillScoreOfCustomer(Customer customer) {
    return phoneBillRepository.findAllByCustomer(customer).stream()
        .filter(phoneBill -> phoneBill.getPaymentDay() != null)
        .map(phoneBill -> phoneBill.getLoanAmount()
            .multiply(BigDecimal.valueOf(ChronoUnit.DAYS
                .between(phoneBill.getDeadline(), phoneBill.getPaymentDay()))))
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .divide(BigDecimal.valueOf(10)).intValue();
  }

  @Override
  public void evaluateModifiedCredit(Customer customer, CreditCustomerStatus creditCustomerStatus) {
    customer.getCredit().stream()
       .filter(credit -> credit.getCustomerApproval()==CreditCustomerStatus.WAITINGFORAPPROVAL)
       .findFirst().get().setCustomerApproval(creditCustomerStatus);

  }
}
