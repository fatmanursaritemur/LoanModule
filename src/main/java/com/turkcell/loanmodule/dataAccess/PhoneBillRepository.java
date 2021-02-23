package com.turkcell.loanmodule.dataAccess;


import com.turkcell.loanmodule.entities.concretes.Customer;
import com.turkcell.loanmodule.entities.concretes.PhoneBill;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneBillRepository  extends JpaRepository<PhoneBill, Long> {
  List<PhoneBill> findAllByCustomer(Customer customer);

}