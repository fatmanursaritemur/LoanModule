package com.turkcell.loanmodule.entities.concretes;

import com.turkcell.loanmodule.entities.enums.EPhotocopy;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "files")
@NoArgsConstructor
@AllArgsConstructor
public class FileDB {
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  private String name;

  private String type;

  @Lob
  private byte[] data;

  @Enumerated(EnumType.STRING)
  private EPhotocopy forWhat;

  @ManyToOne(fetch=FetchType.LAZY, optional=true)
  @JoinColumn(name = "customer_id")
  @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
  @JsonBackReference("file-customer")
  private Customer customer;


  public FileDB(String name, String type, byte[] data) {
    this.name = name;
    this.type = type;
    this.data = data;
  }

  public FileDB(String fileName, String contentType, byte[] bytes, EPhotocopy forwhat, Customer customer) {
    this.name = fileName;
    this.type = contentType;
    this.data = bytes;
    this.forWhat=forwhat;
    this.customer=customer;
  }
}
