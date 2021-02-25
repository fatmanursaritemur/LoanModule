package com.turkcell.loanmodule.dataAccess;

import com.turkcell.loanmodule.entities.concretes.CreditHistory;
import com.turkcell.loanmodule.entities.concretes.Customer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CreditHistoryRepository extends JpaRepository<CreditHistory, Long> {

  List<CreditHistory> findAllByCustomer(Customer customer);

}
