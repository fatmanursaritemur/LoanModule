package com.turkcell.loanmodule.business.concretes;

import com.turkcell.loanmodule.dataAccess.FileDBRepository;
import com.turkcell.loanmodule.entities.concretes.Customer;
import com.turkcell.loanmodule.entities.concretes.FileDB;
import com.turkcell.loanmodule.entities.enums.EPhotocopy;
import java.io.IOException;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

  @Autowired
  private FileDBRepository fileDBRepository;

   FileDB store(MultipartFile file, EPhotocopy forwhat, Customer customer)
      throws IOException {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes(), forwhat, customer);

    return fileDBRepository.save(FileDB);
  }

   FileDB getFile(String id) {
    return fileDBRepository.findById(id).get();
  }

   Stream<FileDB> getAllFiles() {
    return fileDBRepository.findAll().stream();
  }

   Boolean isExistCustomersIdAndCreditPhotocopies(Customer customer) {
    return fileDBRepository.findAllByCustomer(customer)
        .stream()
        .anyMatch(fileDB -> fileDB.getForWhat()==EPhotocopy.CREDIT) &&
        fileDBRepository.findAllByCustomer(customer)
            .stream()
            .anyMatch(fileDB -> fileDB.getForWhat()==EPhotocopy.ID);
  }
}
