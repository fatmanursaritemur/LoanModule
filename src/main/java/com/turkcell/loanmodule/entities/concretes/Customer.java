package com.turkcell.loanmodule.entities.concretes;

import com.turkcell.loanmodule.entities.abstracts.IEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.turkcell.loanmodule.entities.enums.ECustomer;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Customer implements IEntity, Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "customer_id")
  private Long id;

  @NotBlank
  @Column(unique = true)
  private String tcNo;

  private String fullName;

  private String phoneNumber;

  @NotBlank
  @Size(max = 200, min = 20)
  private String address;

  @NotBlank
  @Size(max = 20)
  @Column(unique = true)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  @Column(unique = true)
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  @Column(name = "customer_type")
  @Enumerated(EnumType.STRING)
  private ECustomer customerType;

  @Column(name = "birth_date")
  private LocalDate birthDate;

  @Column(name = "subscription_date")
  private LocalDate subscriptionDate;

  @Column(name = "credit_note")
  private int creditNote = 0;

  @NotNull(message = "monthlyIncome is Mandatory")
  @Column(name = "monthly_Income")
  private BigDecimal monthlyIncome;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JsonBackReference("pen-seller") //sarmal yapı için
  private Set<Credit> credit;


  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<FileDB> fileDB;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<Blacklist> blacklist;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  private Set<CreditHistory> creditHistories;


}
