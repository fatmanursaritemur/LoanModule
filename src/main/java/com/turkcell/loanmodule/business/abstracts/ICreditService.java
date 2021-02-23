package com.turkcell.loanmodule.business.abstracts;

import com.turkcell.loanmodule.entities.concretes.Credit;
import com.turkcell.loanmodule.entities.concretes.Customer;
import com.turkcell.loanmodule.exceptions.AutoRejectCreditException;
import com.turkcell.loanmodule.exceptions.CustomerOnBlacklistException;
import com.turkcell.loanmodule.exceptions.MissingDocumentException;
import java.util.List;
import java.util.Set;

public interface ICreditService {

  Credit apply(Credit credit) throws Exception;

  Credit getCredit(Long id) throws Exception;

  void isExistCustomersIdAndCreditPhotocopies(Customer customer,Credit credit)
      throws MissingDocumentException, AutoRejectCreditException;

  void  isCustomerUnderEighteen(Customer customer, Credit credit)
      throws MissingDocumentException, AutoRejectCreditException;

  void isCustomerOnBlacklist(Customer customer, Credit credit)
      throws CustomerOnBlacklistException, AutoRejectCreditException;

  void isCustomerEverBeenProsecuted(Customer customer, Credit credit)
      throws CustomerOnBlacklistException, AutoRejectCreditException;

  void isGotLoanLessThanAMonth(Customer customer, Credit credit)
      throws AutoRejectCreditException;

  Credit rejectCredit(Credit credit,String reason);

  Customer getCustomer(Long id) throws Exception;

  Set<Credit> getCustomerByCredit(Long id) throws Exception;

  List<Credit> getRiskyLoanApplications();

  void rejectAllRiskyLoanApplications();

  // CreditApplianceResultDto saveCreditApplianceResultDtoByCredit(Credit credit, String reason);
}
