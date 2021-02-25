package com.turkcell.loanmodule.api.controllers;

import com.turkcell.loanmodule.business.abstracts.ICustomerService;
import com.turkcell.loanmodule.business.abstracts.IFileService;
import com.turkcell.loanmodule.entities.concretes.Customer;
import com.turkcell.loanmodule.entities.dtos.CreditApplianceResultDto;
import com.turkcell.loanmodule.entities.dtos.ResponseMessage;
import com.turkcell.loanmodule.entities.enums.CreditCustomerStatus;
import com.turkcell.loanmodule.entities.enums.EPhotocopy;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {

  @Autowired
  ICustomerService customerService;

  @Autowired
  private IFileService fileService;


  @PostMapping("/save")
  public Customer registerCustomer(@Valid @RequestBody Customer customer) {
    return customerService.save(customer);
  }

  @PreAuthorize("hasRole('USER')")
  @PostMapping("/savePhotocopy/{customerId}/{forWhat}")
  public ResponseEntity<ResponseMessage> savePhotocopy(@RequestParam("file") MultipartFile file,
      @PathVariable(value = "customerId") long customerId,
      @PathVariable(value = "forWhat") EPhotocopy forWhat) throws Exception {
    Customer customer = customerService.findById(customerId);
    return fileService.uploadFile(file, customer, forWhat);
  }

  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  @GetMapping("/setcreditNot/{customerId}")
  public Integer setCreditScore(@PathVariable(value = "customerId") long customerId)
      throws Exception {
    Customer customer = customerService.findById(customerId);
    return customerService.setCreditScore(customer);
  }

  @PreAuthorize("hasRole('USER')")
  @GetMapping("evaluateModifiedCredit/{customerId}/{creditCustomerStatus}")
  public void evaluateModifiedCredit(@PathVariable(value = "customerId") long customerId,
      @PathVariable(value = "creditCustomerStatus") CreditCustomerStatus creditCustomerStatus)
      throws Exception {

    Customer customer = customerService.findById(customerId);
    customerService.evaluateModifiedCredit(customer, creditCustomerStatus);
  }

  @PreAuthorize("hasRole('USER')") // son gelen mesajı görür
  @GetMapping("getLastRejectCredit/{customerId}")
  public CreditApplianceResultDto getLastRejectCredit(
      @PathVariable(value = "customerId") long customerId)
      throws Exception {
    Customer customer = customerService.findById(customerId);
    return customerService.getLastRejectCredit(customer);
  }
}
