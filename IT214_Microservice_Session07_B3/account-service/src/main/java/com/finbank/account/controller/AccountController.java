package com.finbank.account.controller;
import com.finbank.account.dto.AmountRequest;
import com.finbank.account.entity.Account;
import com.finbank.account.repository.AccountRepository;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
 private final AccountRepository repo;
 public AccountController(AccountRepository repo){this.repo=repo;}
 @PostMapping public ResponseEntity<?> create(@RequestBody Account a){ if(repo.findByAccountNumber(a.getAccountNumber()).isPresent()) return ResponseEntity.status(409).body("Account already exists"); return ResponseEntity.ok(repo.save(a));}
 @GetMapping("/{accountNumber}") public ResponseEntity<?> get(@PathVariable String accountNumber){return repo.findByAccountNumber(accountNumber).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());}
 @GetMapping("/{accountNumber}/balance") public ResponseEntity<?> balance(@PathVariable String accountNumber){return repo.findByAccountNumber(accountNumber).map(a->ResponseEntity.ok(a.getBalance())).orElseGet(()->ResponseEntity.notFound().build());}
 @PutMapping("/{accountNumber}/debit") public ResponseEntity<?> debit(@PathVariable String accountNumber,@RequestBody AmountRequest r){
   if(r.amount()==null || r.amount().compareTo(BigDecimal.ZERO)<=0) return ResponseEntity.badRequest().body("Amount must be greater than 0");
   return repo.findByAccountNumber(accountNumber).map(a->{if(a.getBalance().compareTo(r.amount())<0) return ResponseEntity.status(400).body("Insufficient balance"); a.setBalance(a.getBalance().subtract(r.amount())); return ResponseEntity.ok(repo.save(a));}).orElseGet(()->ResponseEntity.notFound().build());
 }
 @PutMapping("/{accountNumber}/credit") public ResponseEntity<?> credit(@PathVariable String accountNumber,@RequestBody AmountRequest r){
   if(r.amount()==null || r.amount().compareTo(BigDecimal.ZERO)<=0) return ResponseEntity.badRequest().body("Amount must be greater than 0");
   return repo.findByAccountNumber(accountNumber).map(a->{a.setBalance(a.getBalance().add(r.amount())); return ResponseEntity.ok(repo.save(a));}).orElseGet(()->ResponseEntity.notFound().build());
 }
}
