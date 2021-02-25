package com.turkcell.loanmodule.api.controllers;


import com.turkcell.loanmodule.business.abstracts.IFileService;
import com.turkcell.loanmodule.entities.concretes.Customer;
import com.turkcell.loanmodule.entities.dtos.ResponseFile;
import com.turkcell.loanmodule.entities.dtos.ResponseMessage;
import com.turkcell.loanmodule.entities.enums.EPhotocopy;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/v1/file")
public class FileController {

  @Autowired
  private IFileService fileService;

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file, @RequestBody Customer customer) {
     return  fileService.uploadFile(file, customer, EPhotocopy.CREDIT);
  }


  @GetMapping("/files")
  public ResponseEntity<List<ResponseFile>> getListFiles() {
  return fileService.getListFiles();
  }

  @GetMapping("/files/{id}")
  public ResponseEntity<byte[]> getFile(@PathVariable String id) {
  return  fileService.getFile(id);
  }
}
