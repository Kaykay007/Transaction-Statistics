package com.korede.transactionstatistics.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {
    private BigDecimal amount;
    private long timestamp;

//    public Transaction(BigDecimal amount, long timestamp) {
//        this.amount = amount;
//        this.timestamp = timestamp;
//    }
//
//    public BigDecimal getAmount() {
//        return amount;
//    }
//
//    public long getTimestamp() {
//        return timestamp;
//    }
}