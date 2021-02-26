package com.turkcell.loanmodule.api.controllers;

import com.turkcell.loanmodule.business.abstracts.IReportService;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/api/v1/report")
public class ReportController {

  @Autowired
  IReportService iReportService;

  @RequestMapping(value = "/chartt", method = RequestMethod.GET)
  public String chartt(Model model) {

    Integer numberOfCustomer = iReportService.getNumberOfCustomer();

    model.addAttribute("numberOfCustomer", numberOfCustomer);

    Integer numberOfDailySubscriberRegistration = iReportService
        .numberOfDailySubscriberRegistration();

    model.addAttribute("numberOfDailySubscriberRegistration", numberOfDailySubscriberRegistration);

    Integer numberOfDailyTotalCredit = iReportService.numberOfDailyTotalCredit();

    model.addAttribute("numberOfDailyTotalCredit", numberOfDailyTotalCredit);

    Integer creditGap = iReportService.numberOfDailyTotalCredit();

    model.addAttribute("creditGap", creditGap);

    Integer numberOfEmployee = iReportService.getNumberOfEmployee();

    model.addAttribute("numberOfEmployee", numberOfEmployee);

    iReportService.numberOfCreditsApprovedPerWeek().forEach(((s, integer) -> {
      model.addAttribute(s, integer);
    }));


    final int[] count = {0};
    iReportService.getTrendCreditStatusPerMonthly().forEach((bigIntegers -> {
      count[0]++;
      model.addAttribute("status"+ count[0], bigIntegers);
    }));

    return "chart";
  }

}
