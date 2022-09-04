package com.kevinchamorro.bancopichincha.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ParameterRequestDto {
    private String name;
    private String value;
}
