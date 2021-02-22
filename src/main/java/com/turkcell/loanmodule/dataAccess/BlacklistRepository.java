package com.turkcell.loanmodule.dataAccess;

import com.turkcell.loanmodule.entities.concretes.Blacklist;
import com.turkcell.loanmodule.entities.concretes.Customer;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {
 // Boolean existsBlacklistByCustomerIdExists(Long customerId);
  Boolean existsBlacklistByCustomerAndAndTerminationDate(Customer customer, LocalDate localDate);
  List<Blacklist> findAllByCustomer(Customer customer);
}
