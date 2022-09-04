package com.kevinchamorro.bancopichincha.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionResponseDto {

    private LocalDateTime transactionDate;


    private String transactionType;


    private BigDecimal transactionValue;


    private BigDecimal accountInitialBalance;


    private BigDecimal accountEndingBalance;
}
