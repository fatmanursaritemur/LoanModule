package com.turkcell.loanmodule.api.controllers;

import com.turkcell.loanmodule.business.abstracts.IBlacklistService;
import com.turkcell.loanmodule.entities.concretes.Blacklist;
import com.turkcell.loanmodule.entities.concretes.Customer;
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
@RequestMapping("api/v1/blacklist")
public class BlacklistController {

  @Autowired
  IBlacklistService blacklistService;

  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  @PostMapping("/addCustomer")
  public Blacklist addCustomerToBlacklist(@RequestBody Customer customer) {
    return blacklistService.addCustomerToBlacklist(customer);
  }

  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  @PostMapping("/save")
  public Blacklist save(@Valid @RequestBody Blacklist blacklist) {
    return blacklistService.save(blacklist);
  }

  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  @GetMapping("/getBlacklist")
  public List<Blacklist> getAll() {
    return blacklistService.getAll();
  }

  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  @GetMapping("/get/{id}")
  public Customer get(@PathVariable(value = "id") Long id) throws Exception {
    return blacklistService.getCustomer(id);
  }

  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  public Boolean getCustomerExist(@RequestBody Customer customer) {
    return blacklistService.isCustomerOnBlacklist(customer);
  }

  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  @PostMapping("/findall")
  public List<Blacklist> findAllByCustomer(@RequestBody Customer customer) {
    return blacklistService.findAllByCustomer(customer);
  }
}
