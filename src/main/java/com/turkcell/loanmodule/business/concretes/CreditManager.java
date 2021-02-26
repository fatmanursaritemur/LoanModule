package com.turkcell.loanmodule.business.concretes;

import com.turkcell.loanmodule.business.abstracts.IBlacklistService;
import com.turkcell.loanmodule.business.abstracts.ICreditApplianceResultDtoService;
import com.turkcell.loanmodule.business.abstracts.ICreditHistoryService;
import com.turkcell.loanmodule.business.abstracts.ICreditService;
import com.turkcell.loanmodule.business.abstracts.ICustomerService;
import com.turkcell.loanmodule.business.abstracts.IFileService;
import com.turkcell.loanmodule.business.abstracts.IPropertyService;
import com.turkcell.loanmodule.dataAccess.CreditRepository;
import com.turkcell.loanmodule.dataAccess.LegalProceedingRepository;
import com.turkcell.loanmodule.entities.concretes.Credit;
import com.turkcell.loanmodule.entities.concretes.Customer;
import com.turkcell.loanmodule.entities.enums.CreditStatus;
import com.turkcell.loanmodule.exceptions.AutoRejectCreditException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service

public class CreditManager implements ICreditService {

  @Autowired
  CreditRepository creditRepository;

  @Autowired
  LegalProceedingRepository legalProceedingRepository;

  @Autowired
  IBlacklistService blacklistService;

  @Autowired
  ICreditApplianceResultDtoService creditApplianceResultDtoService;

  @Autowired
  IFileService fileService;

  @Autowired
  ICustomerService customerService;

  @Autowired
  ICreditHistoryService creditHistoryService;

  @Autowired
  IPropertyService propertyService;

  @Transactional
  @Override
  public Credit apply(Credit credit) throws Exception {
    Customer customerChecked = customerService.findById(credit.getCustomer().getId());
    isExistCustomersIdAndCreditPhotocopies(customerChecked, credit);
    isCustomerOnBlacklist(customerChecked, credit);
    isCustomerUnderEighteen(customerChecked, credit);
    isCustomerEverBeenProsecuted(customerChecked, credit);
    isGotLoanLessThanAMonth(customerChecked, credit);
    return creditRepository.save(credit);
  }

  @Override
  public void isExistCustomersIdAndCreditPhotocopies(Customer customer, Credit credit)
      throws AutoRejectCreditException {
    if ( Boolean.FALSE.equals(fileService.isExistCustomersIdAndCreditPhotocopies(customer)) ) {
      rejectCredit(credit, "Customer has missing documents");
      throw new AutoRejectCreditException("Müşterinin eksik belgeleri var olması",
          customer.getId());
    }
  }

  @Override
  public void isCustomerUnderEighteen(Customer customer, Credit credit)
      throws AutoRejectCreditException {
    if ( LocalDate.now().getYear() - customer.getBirthDate().getYear() < 18 ) {
      rejectCredit(credit, "Customer is under 18");
      throw new AutoRejectCreditException("Müşterinin 18 yaşından küçük olması", customer.getId());
    }
  }

  @Override
  public void isCustomerOnBlacklist(Customer customer, Credit credit)
      throws AutoRejectCreditException {
    if ( Boolean.TRUE.equals(blacklistService.isCustomerOnBlacklist(customer)) ) {
      rejectCredit(credit, "Customer in blacklist");
      throw new AutoRejectCreditException("Müşterinin kara listede olması ", customer.getId());
    }
  }

  @Override
  public void isCustomerEverBeenProsecuted(Customer customer, Credit credit)
      throws AutoRejectCreditException {
    if ( Boolean.TRUE.equals(legalProceedingRepository.existsByTcNo(customer.getTcNo())) ) { // ??
      rejectCredit(credit, "Customer was under legal proceedings");
      throw new AutoRejectCreditException("Müşterinin yasal takibe maruz kalmış olması",
          customer.getId());
    }
  }

  @Override
  public void isGotLoanLessThanAMonth(Customer customer, Credit credit)
      throws AutoRejectCreditException {
    if ( Boolean.TRUE.equals(creditHistoryService.isGotLoanLessThanAMonth(customer)) ) { // ??
      rejectCredit(credit, "got loan less than a month");
      throw new AutoRejectCreditException("Bir aydan kısa süre önce kredi alınması",
          customer.getId());
    }
  }

  @Override
  public Credit rejectCredit(Credit credit, String reason) {
    credit.setStatus(CreditStatus.DENIED);
    Credit creditt = creditRepository.save(credit);
    creditApplianceResultDtoService.saveCreditApplianceResultDtoByCredit(creditt, reason);
    return creditt;
  }

  @Override
  public Credit rejectCreditByEmployee(Credit credit, String reason) throws Exception {
    credit.setStatus(CreditStatus.DENIED);
    credit.setEmployee(propertyService.getCurrentEmployee()); //current employee
    Credit creditt = creditRepository.save(credit);
    creditApplianceResultDtoService.saveCreditApplianceResultDtoByCredit(creditt, reason);
    return creditt;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
  public Credit approvalCreditByEmployee(Credit credit) throws Exception {
    credit.setStatus(CreditStatus.APPROVED);
    credit.setEmployee(propertyService.getCurrentEmployee());//current employee
    Credit creditt = creditRepository.save(credit);
    creditHistoryService.saveAllCreditHistory(creditt);
    credit.getCustomer().setSubscriptionDate(LocalDate.now());
    credit.setTermDay(LocalDate.now().getDayOfMonth());
    creditApplianceResultDtoService.saveCreditApplianceResultDtoByCredit(creditt, "approval");
    return creditt;
  }


  @Override
  public List<Credit> getRiskyLoanApplications() {
    return creditRepository.findAll().
        stream()
        .filter(credit -> credit.getStatus() == CreditStatus.APPLIED)
        .filter(credit ->
            credit.getCustomer().getMonthlyIncome().intValue() + credit.getCustomer()
                .getCreditNote() + 1000 <
                (credit.getLoanAmount().intValue()) * (12 / credit.getTerm()))
        .collect(Collectors.toList());

  }

  @Override
  public void rejectAllRiskyLoanApplications() {
    getRiskyLoanApplications().forEach(
        credit -> {
          credit.setStatus(CreditStatus.DENIED);
          creditRepository.save(credit);
          rejectCredit(credit, "loan application found risky");
        }
    );
  }

  @Override
  public List<Credit> getCreditApprovalByDay(LocalDate localDate) {
    return creditRepository.findAll().stream()
        .filter(credit -> credit.getCustomer().getSubscriptionDate().equals(localDate))
        .filter(credit -> credit.getStatus().equals(CreditStatus.APPROVED))
        .collect(Collectors.toList());
  }


  @Override
  public List<Credit> getHighLoanApplications() {
    return getAllLoanApplications()
        .stream()
        .filter(credit -> credit.getTerm() * credit.getLoanAmount().intValue() > 200000)
        .collect(Collectors.toList());
  }

  @Override
  public List<Credit> getNormalLoanApplications() {
    return getAllLoanApplications()
        .stream()
        .filter(credit -> credit.getTerm() * credit.getLoanAmount().intValue() < 200000)
        .collect(Collectors.toList());
  }

  @Override
  public List<Credit> getAllLoanApplications() {
    return creditRepository.findAll()
        .stream()
        .filter(credit -> credit.getStatus() == CreditStatus.APPLIED)
        .collect(Collectors.toList());

  }

  @Override
  public List<List<BigInteger>> findCreditsByCustomerSubscriptionDateMonth(LocalDate localDate) {
    List<List<BigInteger>> trendCreditStatusPerMonthlyList = new ArrayList<>();
    for ( int i = 0; i < 12; i++ ) {
      List<BigInteger> monhtly = new ArrayList<>();
      creditRepository.getAllBetweenDates(localDate, localDate.plusMonths(1))
          .forEach(creditStatus -> monhtly.add(creditStatus));
      trendCreditStatusPerMonthlyList.add(monhtly);
      localDate = localDate.plusMonths(1);
    }
    return trendCreditStatusPerMonthlyList;
  }

  @Override
  public Credit getCredit(Long id) throws Exception {
    return creditRepository.findById(id).orElseThrow(Exception::new);
  }

  /****************************** FOR TEST*********************************/
  @Override
  public Customer getCustomer(Long id) throws Exception {
    getCredit(id).getCustomer().getCredit();
    return getCredit(id).getCustomer();
  }

  @Override
  public Set<Credit> getCustomerByCredit(Long id) throws Exception {
    return getCredit(id).getCustomer().getCredit();
  }


}