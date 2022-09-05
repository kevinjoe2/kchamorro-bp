package com.kevinchamorro.bancopichincha.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class AccountResponseDto {
    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    private String state;
    private String customerNumber;
    private String customerDocumentNumber;
}
