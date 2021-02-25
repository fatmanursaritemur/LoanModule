package com.turkcell.loanmodule.business.abstracts;

import com.turkcell.loanmodule.entities.concretes.Credit;
import com.turkcell.loanmodule.entities.concretes.Employee;
import java.util.List;

public interface IEmployeeService {

  Employee save(Employee employee);

  Employee findById(Long id) throws Exception;

  Employee getEmployeeByUsername(String username);

  List<Employee> findAll();

}
