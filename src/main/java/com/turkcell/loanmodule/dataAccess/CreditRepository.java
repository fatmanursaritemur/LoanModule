package com.turkcell.loanmodule.dataAccess;

import com.turkcell.loanmodule.entities.concretes.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CreditRepository extends JpaRepository<Credit, Long> {

}
