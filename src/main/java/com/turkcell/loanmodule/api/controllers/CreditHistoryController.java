package com.turkcell.loanmodule.api.controllers;

import com.turkcell.loanmodule.business.abstracts.ICreditHistoryService;
import com.turkcell.loanmodule.entities.concretes.Credit;
import com.turkcell.loanmodule.entities.concretes.CreditHistory;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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

  @PostMapping("/savecredithistory")
  void saveAllCreditHistory(@RequestBody Credit credit) throws Exception {
     creditHistoryService.saveAllCreditHistory(credit);
  }
}
