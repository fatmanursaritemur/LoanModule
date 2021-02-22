package com.turkcell.loanmodule.business.abstracts;

import com.turkcell.loanmodule.entities.concretes.Blacklist;
import com.turkcell.loanmodule.entities.concretes.Customer;
import java.util.List;

public interface IBlacklistService {

  Blacklist addCustomerToBlacklist(Customer customer);

  Blacklist save(Blacklist blacklist);

  List<Blacklist> getAll();

  Customer getCustomer(Long id) throws Exception;

  List<Blacklist> findAllByCustomer(Customer customer);

  Boolean isCustomerOnBlacklist(Customer customer);

  Integer getBlacklistScoreOfCustomer(Customer customer);



}
