package com.kevinchamorro.bancopichincha.controller;

import com.kevinchamorro.bancopichincha.dto.ReportTransactionResponseDto;
import com.kevinchamorro.bancopichincha.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequestMapping("report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("findTransactionByFilters")
    public Mono<ResponseEntity<Flux<ReportTransactionResponseDto>>> findTransactionByFilters(
            @RequestParam(value = "fechaDesde", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(value = "fechaHasta", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @RequestParam(value = "NumeroCliente", required = false) String customerNumber
    ) {
        return Mono.just(ResponseEntity.ok(reportService.findTransactionByFilters(dateFrom, dateTo, customerNumber)));
    }
}
