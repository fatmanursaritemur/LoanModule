package com.turkcell.loanmodule.entities.concretes;

import com.turkcell.loanmodule.entities.abstracts.IEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "blacklist")
@NoArgsConstructor
@AllArgsConstructor
public class Blacklist implements IEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

//  @ManyToOne(fetch = FetchType.LAZY, optional = false)
@ManyToOne(fetch=FetchType.LAZY, optional=true)
@JoinColumn(name = "customer_id", nullable = true)
@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
@NotNull
@JsonBackReference("blacklist-customer")
  private Customer customer;


  @NotNull
  private LocalDate entryDate;

  private LocalDate  terminationDate;

  public Blacklist(Customer customer, LocalDate  date) {
    this.customer=customer;
    this.entryDate=date;
  }
}
