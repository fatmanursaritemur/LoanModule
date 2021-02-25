package com.turkcell.loanmodule.business.abstracts;

import com.turkcell.loanmodule.entities.concretes.Credit;
import com.turkcell.loanmodule.entities.concretes.Customer;
import com.turkcell.loanmodule.exceptions.AutoRejectCreditException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ICreditService {

  List<Credit> getHighLoanApplications();

  List<Credit> getNormalLoanApplications();

  List<Credit> getAllLoanApplications();

  Credit getCredit(Long id) throws Exception;

  Credit apply(Credit credit) throws Exception;

  Credit rejectCredit(Credit credit, String reason);

  Credit rejectCreditByEmployee(Credit credit, String reason) throws Exception;

  Credit approvalCreditByEmployee(Credit credit) throws Exception;

  List<Credit> getRiskyLoanApplications();

  Customer getCustomer(Long id) throws Exception;

  void isExistCustomersIdAndCreditPhotocopies(Customer customer, Credit credit)
      throws AutoRejectCreditException;

  void isCustomerUnderEighteen(Customer customer, Credit credit)
      throws AutoRejectCreditException;

  void isCustomerOnBlacklist(Customer customer, Credit credit)
      throws AutoRejectCreditException;

  void isCustomerEverBeenProsecuted(Customer customer, Credit credit)
      throws AutoRejectCreditException;

  void isGotLoanLessThanAMonth(Customer customer, Credit credit)
      throws AutoRejectCreditException;


  Set<Credit> getCustomerByCredit(Long id) throws Exception;

  void rejectAllRiskyLoanApplications();

  List<Credit> getCreditApprovalByDay(LocalDate localDate);


  List<List<BigInteger>> findCreditsByCustomerSubscriptionDateMonth(LocalDate localDate);

}
