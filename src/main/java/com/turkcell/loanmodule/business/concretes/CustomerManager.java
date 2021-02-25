package com.turkcell.loanmodule.business.concretes;

import com.turkcell.loanmodule.business.abstracts.IAuthService;
import com.turkcell.loanmodule.business.abstracts.IBlacklistService;
import com.turkcell.loanmodule.business.abstracts.ICreditApplianceResultDtoService;
import com.turkcell.loanmodule.business.abstracts.ICreditHistoryService;
import com.turkcell.loanmodule.business.abstracts.ICustomerService;
import com.turkcell.loanmodule.dataAccess.CustomerRepository;
import com.turkcell.loanmodule.dataAccess.PhoneBillRepository;
import com.turkcell.loanmodule.entities.concretes.Customer;
import com.turkcell.loanmodule.entities.dtos.CreditApplianceResultDto;
import com.turkcell.loanmodule.entities.enums.CreditCustomerStatus;
import com.turkcell.loanmodule.exceptions.NotFoundException;
import com.turkcell.loanmodule.security.payload.request.SignupRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
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
  ICreditApplianceResultDtoService creditApplianceResultDtoService;

  @Autowired
  ModelMapper modelMapper;


  @Override
  public Customer save(Customer customer) {
    SignupRequest signupRequest = modelMapper.map(customer, SignupRequest.class);
    authService.registerUser(signupRequest);
    return customerRepository.save(customer);
  }

  @Override
  public Customer findById(Long id) throws NotFoundException {
    return customerRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(Customer.class.getSimpleName(), id.toString()));
  }

  @Override
  public Integer setCreditScore(Customer customer) {
    int totalNote = (creditHistoryService.getCreditScoreOfCustomer(customer) +
        blacklistService.getBlacklistScoreOfCustomer(customer) +
        getPhonePillScoreOfCustomer(customer)) / 10;
    customer.setCreditNote(1000 + totalNote);
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
        .filter(credit -> credit.getCustomerApproval() == CreditCustomerStatus.WAITINGFORAPPROVAL)
        .findFirst().get().setCustomerApproval(creditCustomerStatus);

  }

  @Override  // controllera koy
  public CreditApplianceResultDto getLastRejectCredit(Customer customer) {
    return creditApplianceResultDtoService.getCreditApplianceResultDtoByCustomer(customer)
        .stream()
        .sorted(Comparator.comparing(CreditApplianceResultDto::getResultDay))
        .findFirst()
        .get();
  }

  @Override
  public List<Customer> getCustomersRegisteredToday() {
    return customerRepository.findAll().stream().filter(customer -> customer.getSubscriptionDate().equals(
        LocalDate.now())).collect(Collectors.toList());
  }

  @Override
  public List<Customer> findAll(){
    return  customerRepository.findAll();
  }
}
