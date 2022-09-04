package com.kevinchamorro.bancopichincha.error;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ErrorResponse {

    private final LocalDateTime timestamp;

    private final String message;
}
