package com.finbank.transaction.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Table(name="transactions")
public class Transaction {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private String fromAccountNumber; private String toAccountNumber; private BigDecimal amount; private String description; private String status; private String message; private LocalDateTime createdAt;
 public Transaction(){}
 public Transaction(String f,String t,BigDecimal a,String d,String s,String m){fromAccountNumber=f;toAccountNumber=t;amount=a;description=d;status=s;message=m;createdAt=LocalDateTime.now();}
 public Long getId(){return id;} public String getFromAccountNumber(){return fromAccountNumber;} public String getToAccountNumber(){return toAccountNumber;} public BigDecimal getAmount(){return amount;} public String getDescription(){return description;} public String getStatus(){return status;} public String getMessage(){return message;} public LocalDateTime getCreatedAt(){return createdAt;}
}
