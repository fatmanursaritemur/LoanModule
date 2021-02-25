package com.turkcell.loanmodule.entities.concretes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.turkcell.loanmodule.entities.abstracts.IEntity;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "employees")
public class Employee implements IEntity, Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String tcNo;

  private String fullName;

  private String phoneNumber;

  @NotBlank
  @Size(max = 200, min = 20)
  private String address;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  private String jobTitle;

  private String department;

  private LocalDate dateOfEmployment;

  @OneToMany(mappedBy = "employee", cascade = CascadeType.MERGE, fetch = FetchType.LAZY, orphanRemoval = true)
  @JsonBackReference("credit-customer") //sarmal yapı için
  private Set<Credit> Credit;
}
