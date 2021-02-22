package com.turkcell.loanmodule.business.abstracts;

import com.turkcell.loanmodule.entities.concretes.Customer;
import com.turkcell.loanmodule.entities.dtos.ResponseFile;
import com.turkcell.loanmodule.entities.dtos.ResponseMessage;
import com.turkcell.loanmodule.entities.enums.EPhotocopy;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public abstract class IFileService { //abstract idi

  public abstract  ResponseEntity<ResponseMessage> uploadFile(MultipartFile file, Customer customer,
      EPhotocopy forWhat);

  public abstract ResponseEntity<List<ResponseFile>> getListFiles() ;
  public abstract  ResponseEntity<byte[]> getFile(String id);

  public abstract Boolean isExistCustomersIdAndCreditPhotocopies(Customer customer);

  }
