package com.kevinchamorro.bancopichincha.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
public class TransactionResponseDto {

    private String transactionNumber;
    private LocalDateTime transactionDate;
    private String transactionType;
    private BigDecimal transactionValue;
    private BigDecimal accountInitialBalance;
    private BigDecimal accountEndingBalance;
}
