package com.turkcell.loanmodule.dataAccess;

import com.turkcell.loanmodule.entities.concretes.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  Customer findByUsername(String username);
}
