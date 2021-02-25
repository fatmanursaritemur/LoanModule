package com.turkcell.loanmodule.business.concretes;

import com.turkcell.loanmodule.business.abstracts.IBlacklistService;
import com.turkcell.loanmodule.dataAccess.BlacklistRepository;
import com.turkcell.loanmodule.entities.concretes.Blacklist;
import com.turkcell.loanmodule.entities.concretes.Customer;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlacklistManager implements IBlacklistService {

  @Autowired
  BlacklistRepository blacklistRepository;

  @Override
  public Blacklist addCustomerToBlacklist(Customer customer) {
    Blacklist blacklist = new Blacklist(customer, LocalDate.now());
    return blacklistRepository.save(blacklist);
  }

  @Override
  public Blacklist save(Blacklist blacklist) {
    return blacklistRepository.save(blacklist);
  }

  @Override
  public List<Blacklist> getAll() {
    return blacklistRepository.findAll();
  }

  @Override
  public Boolean isCustomerOnBlacklist(Customer customer) {
    return blacklistRepository.existsBlacklistByCustomerAndAndTerminationDate(customer, null);
  }

  @Override
  public Integer getBlacklistScoreOfCustomer(Customer customer) {
    return findAllByCustomer(customer).stream()
        .filter(blacklist -> (blacklist.getTerminationDate() != null))
        .collect(Collectors.toList()).stream().mapToInt(
            forday -> (int) ChronoUnit.DAYS
                .between(forday.getEntryDate(), forday.getTerminationDate()))
        .sum();

  }

  //********************************* For Test***********************************************//
  @Override
  public Customer getCustomer(Long id) throws Exception {
    return blacklistRepository.findById(id).orElseThrow(Exception::new).getCustomer();
  }

  @Override
  public List<Blacklist> findAllByCustomer(Customer customer) {
    return blacklistRepository.findAllByCustomer(customer);
  }


}
