package com.turkcell.loanmodule.dataAccess;

import com.turkcell.loanmodule.entities.concretes.Customer;
import com.turkcell.loanmodule.entities.concretes.FileDB;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {
  List<FileDB> findAllByCustomer(Customer customer);
}
