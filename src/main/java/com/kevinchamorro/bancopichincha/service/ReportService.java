package com.kevinchamorro.bancopichincha.service;

import com.kevinchamorro.bancopichincha.dto.ReportTransactionResponseDto;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface ReportService {

    Flux<ReportTransactionResponseDto> findTransactionByFilters(
            LocalDate dateFrom, LocalDate dateTo, String customerNumber
    );
}
