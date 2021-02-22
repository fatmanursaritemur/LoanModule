package com.turkcell.loanmodule.business.abstracts;

import com.turkcell.loanmodule.entities.concretes.Credit;
import com.turkcell.loanmodule.entities.concretes.CreditHistory;
import com.turkcell.loanmodule.entities.concretes.Customer;
import java.util.List;

public interface ICreditHistoryService {

  List<CreditHistory> findAllByCustomer(Customer customer);

  Integer getCreditScoreOfCustomer(Customer customer);

  void saveAllCreditHistory(Credit credit) throws Exception;

  Boolean isGotLoanLessThanAMonth(Customer customer);
}
