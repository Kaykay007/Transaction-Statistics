package com.korede.transactionstatistics.service;

import com.korede.transactionstatistics.model.Transaction;
import com.korede.transactionstatistics.model.TransactionStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService();
    }

    @Test
    void testAddTransaction_HappyPath() {
        Transaction transaction = new Transaction(new BigDecimal("12.34"), Instant.now().toEpochMilli());
        transactionService.addTransaction(transaction);

        TransactionStatistics statistics = transactionService.getStatistics();
        assertEquals(new BigDecimal("12.34"), statistics.getSum());
        assertEquals(new BigDecimal("12.34"), statistics.getAverage());
        assertEquals(new BigDecimal("12.34"), statistics.getMax());
        assertEquals(new BigDecimal("12.34"), statistics.getMin());
        assertEquals(1, statistics.getCount());
    }

    @Test
    void testAddTransaction_OlderThan30Seconds() {
        long timestamp = System.currentTimeMillis() - 31000; // 31 seconds ago
        Transaction transaction = new Transaction(new BigDecimal("12.34"), timestamp);
        transactionService.addTransaction(transaction);

        TransactionStatistics statistics = transactionService.getStatistics();
        assertEquals(BigDecimal.ZERO.setScale(2), statistics.getSum());
        assertEquals(0, statistics.getCount());
    }

    @Test
    void testClearTransactions() {
        Transaction transaction1 = new Transaction(new BigDecimal("12.34"), Instant.now().toEpochMilli());
        Transaction transaction2 = new Transaction(new BigDecimal("56.78"), Instant.now().toEpochMilli());
        transactionService.addTransaction(transaction1);
        transactionService.addTransaction(transaction2);

        transactionService.clearTransactions();
        TransactionStatistics statistics = transactionService.getStatistics();
        assertEquals(BigDecimal.ZERO.setScale(2), statistics.getSum());
        assertEquals(0, statistics.getCount());
    }
}