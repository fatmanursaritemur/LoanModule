package com.turkcell.loanmodule.entities.concretes;

import com.turkcell.loanmodule.entities.abstracts.IEntity;
import com.turkcell.loanmodule.entities.dtos.CreditApplianceResultDto;
import com.turkcell.loanmodule.entities.enums.CreditStatus;
import com.turkcell.loanmodule.entities.enums.ECredit;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.MonthDay;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "credits")
public class Credit implements IEntity, Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch=FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
  @JsonBackReference("pen-seller")
  private Customer customer;

  private BigDecimal loanAmount;

  @Column(nullable = false)
  private Integer term;

  @Enumerated(EnumType.STRING)
  private CreditStatus status=CreditStatus.APPLIED;

  private MonthDay termDay;

  @Enumerated(EnumType.STRING)
  private ECredit creditType=ECredit.POSTPAID;

  @OneToOne(mappedBy = "credit", cascade = CascadeType.ALL, fetch=FetchType.LAZY,  orphanRemoval = true)
  @JsonBackReference //sarmal yapı için
  private CreditApplianceResultDto creditApplianceResultDto;

  // onaylayan eklenebilir

}
