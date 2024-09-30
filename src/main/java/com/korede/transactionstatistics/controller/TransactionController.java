package com.korede.transactionstatistics.controller;



import com.korede.transactionstatistics.dtos.TransactionRequest;
import com.korede.transactionstatistics.model.Transaction;
import com.korede.transactionstatistics.model.TransactionStatistics;
import com.korede.transactionstatistics.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

//    @PostMapping
//    public ResponseEntity<Void> addTransaction(@RequestBody @Valid TransactionRequest request) {
//        try {
//            BigDecimal amount = new BigDecimal(request.getAmount());
//            long timestamp = Instant.parse(request.getTimestamp()).toEpochMilli();
//            Transaction transaction = new Transaction(amount.setScale(2, RoundingMode.HALF_UP), timestamp);
//            transactionService.addTransaction(transaction);
//            return ResponseEntity.status(HttpStatus.CREATED).build();
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//    }



    @PostMapping
    public ResponseEntity<Void> addTransaction(@RequestBody @Valid TransactionRequest request) {
        BigDecimal amount = new BigDecimal(request.getAmount()).setScale(2, RoundingMode.HALF_UP);
        long timestamp = Instant.parse(request.getTimestamp()).toEpochMilli();

        Transaction transaction = new Transaction(amount, timestamp);

        // Delegate the responsibility of adding the transaction to the service
        transactionService.addTransaction(transaction);

        // If the transaction was older than 30 seconds, the service will handle it
        return ResponseEntity.status(HttpStatus.CREATED).build(); // 201
    }

//    @PostMapping
//    public ResponseEntity<Void> addTransaction(@RequestBody @Valid TransactionRequest request) {
//        try {
//            BigDecimal amount = new BigDecimal(request.getAmount());
//            long timestamp = Instant.parse(request.getTimestamp()).toEpochMilli();
//            Transaction transaction = new Transaction(amount.setScale(2, RoundingMode.HALF_UP), timestamp);
//
//            // Check if the transaction is older than 30 seconds
//            long currentTime = System.currentTimeMillis();
//            if (currentTime - transaction.getTimestamp() > 30000) {
//                return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204
//            }
//
//            // If the transaction is valid and recent, add it
//            transactionService.addTransaction(transaction);
//            return ResponseEntity.status(HttpStatus.CREATED).build(); // 201
//        } catch (IllegalArgumentException e) {
//            System.err.println("Error: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build(); // 422
//        } catch (Exception e) {
//            System.err.println("Error: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400
//        }
//    }

    @GetMapping
    public ResponseEntity<TransactionStatistics> getStatistics() {
        TransactionStatistics statistics = transactionService.getStatistics();
        return ResponseEntity.ok(statistics);
    }

    @DeleteMapping
    public ResponseEntity<Void> clearTransactions() {
        transactionService.clearTransactions();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}