package com.korede.transactionstatistics.model;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionStatistics {
    private BigDecimal sum = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    private BigDecimal max = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    private BigDecimal min = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    private long count = 0;
    public void addTransaction(Transaction transaction) {
        sum = sum.add(transaction.getAmount()).setScale(2, RoundingMode.HALF_UP);
        count++;

        if (count == 1 || transaction.getAmount().compareTo(max) > 0) {
            max = transaction.getAmount();
        }
        if (count == 1 || transaction.getAmount().compareTo(min) < 0) {
            min = transaction.getAmount();
        }
    }

    public void clear() {
        sum = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        max = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        min = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        count = 0;
    }

    public BigDecimal getAverage() {
        return count > 0 ? sum.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
    }

}