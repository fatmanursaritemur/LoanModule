package com.turkcell.loanmodule.api.controllers;

import com.turkcell.loanmodule.business.abstracts.ICustomerService;
import com.turkcell.loanmodule.business.abstracts.IFileService;
import com.turkcell.loanmodule.entities.concretes.Customer;
import com.turkcell.loanmodule.entities.dtos.ResponseMessage;
import com.turkcell.loanmodule.entities.enums.EPhotocopy;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

  // buraya -- user gelecek
  @PostMapping("/savephotocopy/{customerid}/{forwhat}")
  public ResponseEntity<ResponseMessage> savePhotocopy(@RequestParam("file") MultipartFile file, @PathVariable(value = "customerid") long customerid,
      @PathVariable(value = "forwhat") EPhotocopy forwhat)  throws Exception {
    Customer customer=customerService.findById(customerid);
    return fileService.uploadFile(file,customer,forwhat);
  }

}
