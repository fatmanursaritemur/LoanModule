package com.turkcell.loanmodule.business.concretes;

import com.turkcell.loanmodule.business.abstracts.IFileService;
import com.turkcell.loanmodule.entities.concretes.Customer;
import com.turkcell.loanmodule.entities.concretes.FileDB;
import com.turkcell.loanmodule.entities.dtos.ResponseFile;
import com.turkcell.loanmodule.entities.dtos.ResponseMessage;
import com.turkcell.loanmodule.entities.enums.EPhotocopy;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class IFileManager extends IFileService {

  @Autowired
  private FileStorageService storageService;

  @Override
  @Transactional
  public ResponseEntity<ResponseMessage> uploadFile(MultipartFile file, Customer customer,
      EPhotocopy forWhat) {
    String message = "";
    try {
      storageService.store(file, forWhat, customer);

      message = "Uploaded the file successfully: " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseMessage(message));
    }
  }

  @Override
  public ResponseEntity<List<ResponseFile>> getListFiles() {
    List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
      String fileDownloadUri = ServletUriComponentsBuilder
          .fromCurrentContextPath()
          .path("api/v1/file/files/")
          .path(dbFile.getId())
          .toUriString();

      return new ResponseFile(
          dbFile.getName(),
          fileDownloadUri,
          dbFile.getType(),
          dbFile.getData().length);
    }).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(files);
  }

  @Override
  public ResponseEntity<byte[]> getFile(String id) {
    FileDB fileDB = storageService.getFile(id);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + fileDB.getName() + "\"")
        .body(fileDB.getData());
  }

  @Override
  public Boolean isExistCustomersIdAndCreditPhotocopies(Customer customer) {
    return storageService.isExistCustomersIdAndCreditPhotocopies(customer);
  }
}
