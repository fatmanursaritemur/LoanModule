package com.turkcell.loanmodule.entities.concretes;

import com.turkcell.loanmodule.entities.abstracts.IEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.turkcell.loanmodule.entities.enums.ECustomer;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Customer implements IEntity, Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name =  "customer_id")
  private Long id;

 // @UniqueElements
  private String tcNo;

  private String fullName;

 //@ValidPhoneNumber(message="Please enter a valid phone number")
  private String phoneNumber;

  @NotBlank
  @Size(max = 200, min = 20)
  private String address;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email; // unique yap

  @NotBlank
  @Size(max = 120)
  private String password;

  @Column(name = "customer_type")
  @Enumerated(EnumType.STRING)
  private ECustomer customerType;

  @Column(name = "birth_date")
  private LocalDate birthDate; // belki 18 yaşında mı bakılır

  @Column(name = "subscription_date")
  private LocalDate subscriptionDate;

  @Column(name = "credit_note")
  private int creditNote=0;

 @Column(name =  "monthly_Income")
  private  BigDecimal monthlyIncome;

 @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
@JsonBackReference("pen-seller") //sarmal yapı için
 private Credit credit;

 @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL,fetch=FetchType.LAZY)
//@JsonManagedReference("file-customer") //sarmal yapı için
 private Set<FileDB> fileDB;

 @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL,fetch=FetchType.LAZY)
 //@JsonBackReference //sarmal yapı için
//@JsonManagedReference("blacklist-customer")
 private Set<Blacklist> blacklist;

 @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL,fetch=FetchType.LAZY)
 //@JsonBackReference //sarmal yapı için
// @JsonManagedReference("credithistories-customer")
 private Set<CreditHistory> creditHistories;

 public void setCredit(Credit credit) {
  this.credit = credit;
  credit.setCustomer(this);
 }
}
