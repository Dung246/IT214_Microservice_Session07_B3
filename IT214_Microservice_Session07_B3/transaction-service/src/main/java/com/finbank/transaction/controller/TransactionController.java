package com.finbank.transaction.controller;
import com.finbank.transaction.dto.TransferRequest;
import com.finbank.transaction.entity.Transaction;
import com.finbank.transaction.repository.TransactionRepository;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.util.Map;
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
 private final RestTemplate restTemplate; private final TransactionRepository repo;
 public TransactionController(RestTemplate rt,TransactionRepository repo){this.restTemplate=rt;this.repo=repo;}
 @PostMapping("/transfer")
 public ResponseEntity<?> transfer(@RequestBody TransferRequest r){
   if(r.amount()==null || r.amount().compareTo(BigDecimal.ZERO)<=0) return failed(r,"Amount must be greater than 0");
   if(r.fromAccountNumber()==null || r.toAccountNumber()==null) return failed(r,"Account number is required");
   if(r.fromAccountNumber().equals(r.toAccountNumber())) return failed(r,"Source and destination accounts must be different");
   String base="http://ACCOUNT-SERVICE/api/accounts/";
   try{
     ResponseEntity<Map> from=restTemplate.getForEntity(base+r.fromAccountNumber(),Map.class);
     if(!from.getStatusCode().is2xxSuccessful()) return failed(r,"Source account not found");
     ResponseEntity<Map> to=restTemplate.getForEntity(base+r.toAccountNumber(),Map.class);
     if(!to.getStatusCode().is2xxSuccessful()) return failed(r,"Destination account not found");
     restTemplate.put(base+r.fromAccountNumber()+"/debit",Map.of("amount",r.amount()));
     restTemplate.put(base+r.toAccountNumber()+"/credit",Map.of("amount",r.amount()));
     Transaction tx=repo.save(new Transaction(r.fromAccountNumber(),r.toAccountNumber(),r.amount(),r.description(),"SUCCESS","Transfer successful"));
     return ResponseEntity.ok(tx);
   } catch(RestClientException e){return failed(r,"Account Service unavailable or transfer failed: "+e.getMessage());}
 }
 private ResponseEntity<?> failed(TransferRequest r,String msg){Transaction tx=repo.save(new Transaction(r.fromAccountNumber(),r.toAccountNumber(),r.amount(),r.description(),"FAILED",msg));return ResponseEntity.badRequest().body(tx);}
}
