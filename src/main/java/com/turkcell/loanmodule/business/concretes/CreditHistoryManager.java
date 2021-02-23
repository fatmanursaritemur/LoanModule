package com.turkcell.loanmodule.business.concretes;

import com.turkcell.loanmodule.business.abstracts.ICreditHistoryService;
import com.turkcell.loanmodule.business.abstracts.ICreditService;
import com.turkcell.loanmodule.dataAccess.CreditHistoryRepository;
import com.turkcell.loanmodule.entities.concretes.Credit;
import com.turkcell.loanmodule.entities.concretes.CreditHistory;
import com.turkcell.loanmodule.entities.concretes.Customer;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class CreditHistoryManager implements ICreditHistoryService {

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  CreditHistoryRepository creditHistoryRepository;

  @Autowired
  ICreditService creditService;

  @Override
  public List<CreditHistory> findAllByCustomer(Customer customer) {
    return creditHistoryRepository.findAllByCustomer(customer);
  }

  @Override
  public BigDecimal getCreditScoreOfCustomer(Customer customer) { ///
    return creditHistoryRepository.findAllByCustomer(customer).stream()
        .filter(creditHistory -> creditHistory.getPaymentDay()!=null)
        .map(creditHistory -> creditHistory.getLoanAmount()
            .multiply(BigDecimal.valueOf(ChronoUnit.DAYS
                .between(creditHistory.getDeadline(), creditHistory.getPaymentDay()))))
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .divide(BigDecimal.valueOf(10));
  }

  @Override
  public CreditHistory save(CreditHistory creditHistory) {
    return creditHistoryRepository.save(creditHistory);
  }

  @Override
  public void saveAllCreditHistory(Credit credit) throws Exception {
    Credit credit1 = creditService.getCredit(credit.getId());
    CreditHistory creditHistory = modelMapper.map(credit1, CreditHistory.class);
    creditHistory.setCustomer(credit1.getCustomer());
    LocalDate localDate = credit1.getCustomer().getSubscriptionDate().plusMonths(1);
    saveEachCreditHistory(creditHistory,credit1.getTerm(),localDate);
   /* for ( int i = 0; i < credit1.getTerm(); i++ ) { // ayrı fonksiyon yap
      creditHistory.setDeadline(localDate);
      creditHistoryRepository.save(creditHistory);
      localDate = localDate.plusMonths(1);
    }*/
  }

  @Override
  public void saveEachCreditHistory(CreditHistory creditHistory, int term, LocalDate localDate ) {
    for ( int i = 0; i < term; i++ ) {
      creditHistory.setDeadline(localDate);
      creditHistoryRepository.save(creditHistory);
      localDate = localDate.plusMonths(1);
    }
  }

  @Override
  public Boolean isGotLoanLessThanAMonth(
      Customer customer) {
    return findAllByCustomer(customer).stream().anyMatch(
        creditHistory -> creditHistory.getDeadline().isBefore(LocalDate.now().minusMonths(1)));
  }
}
