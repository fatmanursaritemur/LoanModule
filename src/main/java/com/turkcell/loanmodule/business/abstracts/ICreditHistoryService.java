package com.turkcell.loanmodule.business.abstracts;

import com.turkcell.loanmodule.entities.concretes.Credit;
import com.turkcell.loanmodule.entities.concretes.CreditHistory;
import com.turkcell.loanmodule.entities.concretes.Customer;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ICreditHistoryService {

  List<CreditHistory> findAllByCustomer(Customer customer);

  BigDecimal getCreditScoreOfCustomer(Customer customer);

  CreditHistory save(CreditHistory creditHistory);
  void saveAllCreditHistory(Credit credit) throws Exception;

  void saveEachCreditHistory(CreditHistory creditHistory, int term, LocalDate localDate);

  Boolean isGotLoanLessThanAMonth(Customer customer);
}
