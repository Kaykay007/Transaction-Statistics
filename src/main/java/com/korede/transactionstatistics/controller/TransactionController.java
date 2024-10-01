package com.korede.transactionstatistics.controller;

import com.korede.transactionstatistics.dtos.TransactionRequest;
import com.korede.transactionstatistics.model.Transaction;
import com.korede.transactionstatistics.model.TransactionStatistics;
import com.korede.transactionstatistics.service.TransactionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

@RestController
@RequestMapping("/transaction")
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Void> addTransaction(@RequestBody @Valid TransactionRequest request) {
        BigDecimal amount = new BigDecimal(request.getAmount()).setScale(2, RoundingMode.HALF_UP);
        long timestamp = Instant.parse(request.getTimestamp()).toEpochMilli();

        Transaction transaction = new Transaction(amount, timestamp);
        // Check if the transaction is older than 30 seconds
            long currentTime = System.currentTimeMillis();
            if (currentTime - transaction.getTimestamp() > 30000) {
                log.info("checking current time!!! {}", currentTime);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        transactionService.addTransaction(transaction);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<TransactionStatistics> getStatistics() {
        TransactionStatistics statistics = transactionService.getStatistics();
        return ResponseEntity.ok(statistics);
    }

    @DeleteMapping
    public ResponseEntity<Void> DeleteTransactions() {
        transactionService.DeleteTransactions();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}