package com.kevinchamorro.bancopichincha.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class TransactionRequestDto {
    private final String accountNumber;
    private final BigDecimal transactionValue;
}
