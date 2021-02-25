package com.turkcell.loanmodule.api.controllers;

import com.turkcell.loanmodule.business.abstracts.ICreditService;
import com.turkcell.loanmodule.entities.concretes.Credit;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/v1/credit")
public class CreditController {

  @Autowired
  ICreditService creditService;

  @PreAuthorize("hasRole('USER')")
  @PostMapping("/applyy")
  public Credit applyCredit(@Valid @RequestBody Credit credit)
      throws Exception {
    return creditService.apply(credit);
  }

  @PreAuthorize("hasRole('MODERATOR')")
  @GetMapping("/getRisklyLoan")
  public List<Credit> getRiskyLoanApplications() {
    return creditService.getRiskyLoanApplications();
  }

  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  @GetMapping("/rejectAllRiskyLoanApplications")
  public void rejectAllRiskyLoanApplications() {
    creditService.rejectAllRiskyLoanApplications();
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/getHighLoanApplications") // for admin --senior official
  public List<Credit> getHighLoanApplications() {
    return creditService.getHighLoanApplications();
  }

  @PreAuthorize("hasRole('MODERATOR')")
  @GetMapping("/getNormalLoanApplications")
  public List<Credit> getNormalLoanApplications() {
    return creditService.getNormalLoanApplications();
  }

  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  @GetMapping("/approvalCreditByEmployee/{creditId}")
  public Credit approvalCreditByEmployee(@PathVariable(value = "creditId") Long id)
      throws Exception {
    Credit credit = creditService.getCredit(id);
    return creditService.approvalCreditByEmployee(credit);
  }

  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  @PostMapping("/rejectCreditByEmployee/{creditId}")
  public Credit rejectCreditByEmployee(@PathVariable(value = "creditId") Long id,
      @RequestBody String reason) throws Exception {
    Credit credit = creditService.getCredit(id);
    return creditService.rejectCreditByEmployee(credit, reason);
  }


  /********************************* For Test ***************************/

  @PostMapping("/findCreditsByCustomerSubscriptionDateMonth")
  public List<Credit> findCreditsByCustomerSubscriptionDateMonth(@RequestBody LocalDate localDate) {
    creditService.findCreditsByCustomerSubscriptionDateMonth(localDate);
    return null;
  }

}
