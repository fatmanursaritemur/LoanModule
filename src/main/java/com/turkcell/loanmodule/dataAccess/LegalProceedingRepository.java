package com.turkcell.loanmodule.dataAccess;

import com.turkcell.loanmodule.entities.concretes.LegalProceeding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegalProceedingRepository extends JpaRepository<LegalProceeding, String> {

  Boolean existsByTcNo(String tcNo);
}
