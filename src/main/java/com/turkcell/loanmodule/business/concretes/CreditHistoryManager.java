package com.turkcell.loanmodule.business.concretes;

import com.turkcell.loanmodule.business.abstracts.ICreditHistoryService;
import com.turkcell.loanmodule.business.abstracts.ICreditService;
import com.turkcell.loanmodule.dataAccess.CreditHistoryRepository;
import com.turkcell.loanmodule.entities.concretes.Credit;
import com.turkcell.loanmodule.entities.concretes.CreditHistory;
import com.turkcell.loanmodule.entities.concretes.Customer;
import com.turkcell.loanmodule.payload.request.SignupRequest;
import java.time.LocalDate;
import java.util.ArrayList;
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
  public Integer getCreditScoreOfCustomer(Customer customer) {
    return null;
  }

  @Override
  public void saveAllCreditHistory(Credit credit) throws Exception {
    Credit credit1=creditService.getCredit(credit.getId());
    CreditHistory creditHistory = modelMapper.map(credit1, CreditHistory.class);
    creditHistory.setCustomer(credit1.getCustomer());
    LocalDate localDate = credit1.getCustomer().getSubscriptionDate().plusMonths(1);
    for ( int i = 0; i < credit1.getTerm(); i++ ) { // ayrı fonksiyon yap
      creditHistory.setDeadline(localDate);
      creditHistoryRepository.save(creditHistory);
      localDate=localDate.plusMonths(1);
    }
  }

  @Override
  public Boolean isGotLoanLessThanAMonth(Customer customer) { // kontrol et, true demek kredi alabilir demek ! olarak al
   return findAllByCustomer(customer).stream().anyMatch(
        creditHistory -> creditHistory.getDeadline().isBefore(LocalDate.now().minusMonths(1)));
  }
}
