package com.kevinchamorro.bancopichincha.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ParameterResponseDto {
    private String name;
    private String value;
}
