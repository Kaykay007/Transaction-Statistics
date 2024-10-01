package com.korede.transactionstatistics.service;

import com.korede.transactionstatistics.exception.InvalidTransactionException;
import com.korede.transactionstatistics.model.Transaction;
import com.korede.transactionstatistics.model.TransactionStatistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentLinkedQueue;


@Service
@Slf4j
public class TransactionService {

    private final ConcurrentLinkedQueue<Transaction> transactions = new ConcurrentLinkedQueue<>();
    private final TransactionStatistics statistics = new TransactionStatistics();



    public void addTransaction(Transaction transaction) {
        log.info("Adding transaction method!!: {}", transaction);
        long currentTime = System.currentTimeMillis();

        /* Check for future timestamp and Proceed to add the transaction an update the stats**/

        if (transaction.getTimestamp() > currentTime) {
            throw new InvalidTransactionException("Transaction timestamp is in the future.");
        }

        transactions.add(transaction);
        statistics.addTransaction(transaction);
    }


    public TransactionStatistics getStatistics() {
        cropOutOldTransactions();
        return statistics;
    }

    public void DeleteTransactions() {
        transactions.clear();
        statistics.clear();
    }

    private void cropOutOldTransactions() {
        long cutoffTime = System.currentTimeMillis() - 30000;

        ConcurrentLinkedQueue<Transaction> validTransactions = new ConcurrentLinkedQueue<>();
        while (!transactions.isEmpty()) {
            Transaction transaction = transactions.poll();
            if (transaction.getTimestamp() >= cutoffTime) {
                validTransactions.add(transaction);
            }
        }

        statistics.clear();
        for (Transaction transaction : validTransactions) {
            statistics.addTransaction(transaction);
        }
        // Update the transactions queue with valid transactions
        transactions.addAll(validTransactions);
    }

}

