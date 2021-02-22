package com.turkcell.loanmodule.entities.dtos;


import com.turkcell.loanmodule.entities.concretes.Credit;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class CreditApplianceResultDto {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch= FetchType.LAZY)
  @JoinColumn(name = "credit_id")
  @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
  @JsonBackReference
  private Credit credit;

  private Integer term;

  private BigDecimal loanAmount;

  private String reason;
}
