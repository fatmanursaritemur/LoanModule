package com.turkcell.loanmodule.api.controllers;

import com.turkcell.loanmodule.business.abstracts.IBlacklistService;
import com.turkcell.loanmodule.entities.concretes.Blacklist;
import com.turkcell.loanmodule.entities.concretes.Customer;
import java.util.List;
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
@RequestMapping("api/v1/blacklist")
public class BlacklistController {

  @Autowired
  IBlacklistService blacklistService;

  // user && moderator
  @PostMapping("/addCustomer")
  public Blacklist addCustomerToBlacklist( @RequestBody Customer customer) {
    return blacklistService.addCustomerToBlacklist(customer);
  }

  @PostMapping("/save")
  public Blacklist save(@Valid @RequestBody Blacklist blacklist) {
    return blacklistService.save(blacklist);
  }

  @GetMapping("/getBlacklist")
  public List<Blacklist> getAll() {
    return blacklistService.getAll();
  }

  @GetMapping("/get/{id}")
  public Customer get(@PathVariable(value = "id") Long id) throws Exception {
    return blacklistService.getCustomer(id);
  }

  @PostMapping("/getc")
  public Boolean getCustomerExit(@RequestBody Customer customer)
  {
    return blacklistService.isCustomerOnBlacklist(customer);
  }

  @PostMapping("/findall")
  public List<Blacklist> findAllByCustomer(@RequestBody Customer customer) {
    return blacklistService.findAllByCustomer(customer);
  }
}
