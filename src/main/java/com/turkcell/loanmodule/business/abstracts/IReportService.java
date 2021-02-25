package com.turkcell.loanmodule.business.abstracts;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface IReportService {

  Integer numberOfDailySubscriberRegistration();

  Integer numberOfDailyTotalCredit();

  Map<String, Integer> numberOfCreditsApprovedPerWeek();

  BigDecimal getCreditGap();

  List<List<BigInteger>> getTrendCreditStatusPerMonthly();

  Integer getNumberOfCustomer();

  Integer getNumberOfEmployee();

}
