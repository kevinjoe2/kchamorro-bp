package com.kevinchamorro.bancopichincha.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;

@Builder
@Getter
public class AccountRequestDto {
    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    private String state;
}
