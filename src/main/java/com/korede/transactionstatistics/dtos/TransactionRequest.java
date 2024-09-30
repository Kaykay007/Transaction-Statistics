package com.korede.transactionstatistics.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TransactionRequest {
    @NotNull
    private String amount;


    @NotNull
    private String timestamp;
}