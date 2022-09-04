package com.kevinchamorro.bancopichincha.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccountRequestDto {
    private String accountType;
    private String customerDocumentNumber;
}
