package com.turkcell.loanmodule.business.concretes;

import com.turkcell.loanmodule.business.abstracts.ICreditHistoryService;
import com.turkcell.loanmodule.business.abstracts.ICreditService;
import com.turkcell.loanmodule.business.abstracts.ICustomerService;
import com.turkcell.loanmodule.business.abstracts.IEmployeeService;
import com.turkcell.loanmodule.business.abstracts.IReportService;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportManager implements IReportService {

  @Autowired
  ICustomerService customerService;

  @Autowired
  ICreditService creditService;

  @Autowired
  ICreditHistoryService creditHistoryService;

  @Autowired
  IEmployeeService employeeService;

  @Override
  public Integer numberOfDailySubscriberRegistration() {
    return customerService.getCustomersRegisteredToday().size();
  }

  @Override
  public Integer numberOfDailyTotalCredit() { // big decimal olursa dene
    return creditService.getCreditApprovalByDay(LocalDate.now())
        .stream()
        .map(credit -> credit.getLoanAmount())
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .intValue();
  }

  @Override
  public Map<String, Integer> numberOfCreditsApprovedPerWeek() {
    Map<String, Integer> creditApprovedPerWeek = new HashMap<>();
    LocalDate day = LocalDate.now();
    for ( int i = 0; i < 7; i++ ) {
      creditApprovedPerWeek
          .put(day.getDayOfWeek().toString(), creditService.getCreditApprovalByDay(day).size());
      day = day.minusDays(1);
    }
    return creditApprovedPerWeek;
  }

  @Override
  public BigDecimal getCreditGap() {
    return creditHistoryService.getOutstandingLoans().stream()
        .map(creditHistory -> creditHistory.getLoanAmount())
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }


  @Override
  public List<List<BigInteger>> getTrendCreditStatusPerMonthly() {
    List<List<BigInteger>> trendCreditStatusPerMonthlyList = creditService
        .findCreditsByCustomerSubscriptionDateMonth(LocalDate.now());
    for ( List<BigInteger> a : trendCreditStatusPerMonthlyList ) {
      int size=a.size();
      for ( int i = 0; i < 5 - size; i++ ) {
        a.add(BigInteger.ZERO);
      }
    }
    return trendCreditStatusPerMonthlyList;
  }

  @Override
  public Integer getNumberOfCustomer() {
    return customerService.findAll().size();
  }

  @Override
  public Integer getNumberOfEmployee() {
    return employeeService.findAll().size();
  }

}
