package com.finbank.account.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity
@Table(name="accounts")
public class Account {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(unique=true,nullable=false) private String accountNumber;
 private String ownerName;
 private BigDecimal balance;
 public Account(){}
 public Account(String accountNumber,String ownerName,BigDecimal balance){this.accountNumber=accountNumber;this.ownerName=ownerName;this.balance=balance;}
 public Long getId(){return id;} public String getAccountNumber(){return accountNumber;} public void setAccountNumber(String v){accountNumber=v;}
 public String getOwnerName(){return ownerName;} public void setOwnerName(String v){ownerName=v;}
 public BigDecimal getBalance(){return balance;} public void setBalance(BigDecimal v){balance=v;}
}
