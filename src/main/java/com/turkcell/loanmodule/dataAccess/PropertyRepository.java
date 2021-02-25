package com.turkcell.loanmodule.dataAccess;

import com.turkcell.loanmodule.entities.concretes.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, String> {


  @Query(value = "SELECT propertyValue FROM Property u WHERE u.propertyName = ?1")
  String findPropertyValueByPropertyName(String propertyName);

  @Query(value = "SELECT u FROM Property u WHERE u.propertyName = ?1")
  Property findPropertyByPropertyName(String propertyName);
}
