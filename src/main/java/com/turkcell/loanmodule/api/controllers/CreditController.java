package com.turkcell.loanmodule.api.controllers;

import com.turkcell.loanmodule.business.abstracts.ICreditService;
import com.turkcell.loanmodule.entities.concretes.Credit;
import com.turkcell.loanmodule.entities.concretes.Customer;
import com.turkcell.loanmodule.exceptions.CustomerOnBlacklistException;
import com.turkcell.loanmodule.exceptions.MissingDocumentException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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


/*  @PostMapping("/apply")
  public Credit applyCredit(@Valid @RequestBody Credit credit) {
    return creditService.apply(credit);
  }*/

  //@PreAuthorize("hasRole('USER')")
  @PostMapping("/applyy")
  public Credit applyCredit(@Valid @RequestBody Credit credit)
      throws Exception {
    return creditService.apply(credit);
  }

  @GetMapping("/fe/{id}")
  public Credit getCredit(@PathVariable(value = "id") Long id ) throws Exception {
    return creditService.getCredit(id);
  }

  @GetMapping("/fee/{id}")
  public Customer getCustomer(@PathVariable(value = "id") Long id ) throws Exception {
    return creditService.getCustomer(id);
  }

  @GetMapping("/cus/{id}")
  public Credit getCustomerByCrdit(@PathVariable(value = "id") Long id ) throws Exception {
    return creditService.getCustomerByCredit(id);
  }
}
