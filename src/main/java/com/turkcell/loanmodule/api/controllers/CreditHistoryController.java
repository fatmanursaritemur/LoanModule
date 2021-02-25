package com.turkcell.loanmodule.api.controllers;

import com.turkcell.loanmodule.business.abstracts.ICreditHistoryService;
import com.turkcell.loanmodule.business.abstracts.ICustomerService;
import com.turkcell.loanmodule.entities.concretes.Credit;
import com.turkcell.loanmodule.entities.concretes.CreditHistory;
import com.turkcell.loanmodule.entities.concretes.Customer;
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
@RequestMapping("api/v1/creditHistory")
public class CreditHistoryController {

  @Autowired
  ICreditHistoryService creditHistoryService;

  @Autowired
  ICustomerService customerService;

  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  @PostMapping("/saveCreditHistoryWithCredit")
  void saveAllCreditHistory(@RequestBody Credit credit) throws Exception {
    creditHistoryService.saveAllCreditHistory(credit);
  }

  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  @PostMapping("/saveCreditHistoryWithCreditHistory")
  CreditHistory save(@RequestBody CreditHistory creditHistory) {
    return creditHistoryService.save(creditHistory);
  }

  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  @GetMapping("/getscore/{id}")
  Integer getCreditScoreOfCustomer(@PathVariable(value = "id") Long id) throws Exception {
    Customer customer = customerService.findById(id);
    return creditHistoryService.getCreditScoreOfCustomer(customer);
  }
}
