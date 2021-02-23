package com.turkcell.loanmodule.api.controllers;

import com.turkcell.loanmodule.business.abstracts.ICreditHistoryService;
import com.turkcell.loanmodule.business.abstracts.ICustomerService;
import com.turkcell.loanmodule.entities.concretes.Credit;
import com.turkcell.loanmodule.entities.concretes.CreditHistory;
import com.turkcell.loanmodule.entities.concretes.Customer;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// **************************************gereksiz**************************
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/v1/creditHistory")
public class CreditHistoryController {

  @Autowired
  ICreditHistoryService creditHistoryService;

  @Autowired
  ICustomerService customerService;

  @PostMapping("/savecredithistory")
  void saveAllCreditHistory(@RequestBody Credit credit) throws Exception {
     creditHistoryService.saveAllCreditHistory(credit);
  }

  @PostMapping("/getscore")
  CreditHistory save(@RequestBody CreditHistory creditHistory) throws Exception {
   // Customer customer=customerService.findById(creditHistory.getCustomer().getId());
  return   creditHistoryService.save(creditHistory);
   // return  creditHistoryService.getCreditScoreOfCustomer(customer);
  }

  @GetMapping("/gettscore/{id}")
  BigDecimal getCreditScoreOfCustomer(@PathVariable(value = "id") Long id) throws Exception {
    Customer customer=customerService.findById(id);
    return  creditHistoryService.getCreditScoreOfCustomer(customer);
  }
}
