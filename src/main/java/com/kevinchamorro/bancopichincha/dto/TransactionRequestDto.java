package com.kevinchamorro.bancopichincha.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class TransactionRequestDto {
    private final String accountNumber;
    private final BigDecimal transactionValue;
}
