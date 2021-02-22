package com.turkcell.loanmodule.entities.concretes;

import com.turkcell.loanmodule.validation.ValidPhoneNumber;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

@Data
@Entity
@Table(name = "employees")
public class Employee  {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @UniqueElements
   private String tcNo;

   private String fullName;

   @ValidPhoneNumber(message="Please enter a valid phone number")
   private String phoneNumber;

   @NotBlank
   @Size(max = 200, min = 20)
   @Email
   private String address;

   @NotBlank
   @Size(max = 20)
   private String username;

   @NotBlank
   @Size(max = 50)
   @Email
   private String email;

   @NotBlank
   @Size(max = 120)
   private String password;

   private  String jobTitle;

   private  String department;

   private LocalDate dateOfEmployment;
}
