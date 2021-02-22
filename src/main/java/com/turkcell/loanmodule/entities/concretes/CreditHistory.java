package com.turkcell.loanmodule.entities.concretes;

import com.turkcell.loanmodule.entities.abstracts.IEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonBackReference;


@Data
@Entity
@Table(name = "payment_histories")
public class CreditHistory implements IEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "customer_id", nullable = false)
  @JsonBackReference("credithistories-customer")
  private Customer customer;

  private BigDecimal loanAmount;

  private LocalDate deadline;

  private LocalDate paymentDay;

}
