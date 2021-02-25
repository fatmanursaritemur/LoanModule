package com.turkcell.loanmodule.api.controllers;

import com.turkcell.loanmodule.dataAccess.PhoneBillRepository;
import com.turkcell.loanmodule.entities.concretes.PhoneBill;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

  @Autowired
  PhoneBillRepository phoneBillRepository;

  @PostMapping("/all")
  public String allAccess(@RequestBody String aa) {

    return "Public Content.";
  }

  @GetMapping("/allt")
  public long testnum() {
    LocalDate end = LocalDate.parse("2021-01-22");
    LocalDate start = LocalDate.parse("2021-02-25");
    return ChronoUnit.DAYS.between(start, end);
    //	return "Public Content.";
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public String userAccess() {
    return "User Content.";
  }

  @GetMapping("/mod")
  @PreAuthorize("hasRole('MODERATOR')")
  public String moderatorAccess() {
    return "Moderator Board.";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return "Admin Board.";
  }

  @PostMapping("/savePhoneBill")
  public PhoneBill savePhoneBill(@RequestBody PhoneBill phoneBill){
    return phoneBillRepository.save(phoneBill);
  }


}
