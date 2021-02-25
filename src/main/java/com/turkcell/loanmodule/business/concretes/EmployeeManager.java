package com.turkcell.loanmodule.business.concretes;

import com.turkcell.loanmodule.business.abstracts.IAuthService;
import com.turkcell.loanmodule.business.abstracts.IEmployeeService;
import com.turkcell.loanmodule.dataAccess.EmployeeRepository;
import com.turkcell.loanmodule.entities.concretes.Employee;
import com.turkcell.loanmodule.exceptions.NotFoundException;
import com.turkcell.loanmodule.security.payload.request.SignupRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class EmployeeManager implements IEmployeeService {

  @Autowired
  EmployeeRepository employeeRepository;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  IAuthService authService;

  @Override
  public Employee save(Employee employee) {
    SignupRequest signupRequest = modelMapper.map(employee, SignupRequest.class);
    Set<String> role = new HashSet<String>();
    role.add("mod");
    signupRequest.setRole(role);
    authService.registerUser(signupRequest);
    return employeeRepository.save(employee);
  }

  @Override
  public Employee findById(Long id) throws NotFoundException {
    return employeeRepository.findById(id)
        .orElseThrow(
            () -> new NotFoundException(Employee.class.getSimpleName(), String.valueOf(id)));

  }

  @Override
  public Employee getEmployeeByUsername(String username) {
    return employeeRepository.findByUsername(username);
  }

  @Override
  public List<Employee> findAll() {
    return employeeRepository.findAll();
  }
}
