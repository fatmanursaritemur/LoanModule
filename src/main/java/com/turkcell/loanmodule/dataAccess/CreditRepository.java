package com.turkcell.loanmodule.dataAccess;

import com.turkcell.loanmodule.entities.concretes.Credit;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository

public interface CreditRepository extends JpaRepository<Credit, Long> {

  @Query(value = "SELECT count(status)  FROM credits WHERE customer_id IN (SELECT customer_id FROM customers WHERE subscription_date BETWEEN ?1 AND ?2) GROUP BY status ", nativeQuery = true)
  List<BigInteger> getAllBetweenDates(@Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate);
}
