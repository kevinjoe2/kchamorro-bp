package com.kevinchamorro.bancopichincha.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class CustomerResponseDto {
    private Long id;
    private String name;
    private String gender;
    private LocalDate dateBirth;
    private String documentNumber;
    private String address;
    private String phone;
    private String customerNumber;
    private String password;
    private String state;
}
