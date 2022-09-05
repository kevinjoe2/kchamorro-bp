package com.kevinchamorro.bancopichincha.service.Impl;

import com.kevinchamorro.bancopichincha.dto.ReportTransactionResponseDto;
import com.kevinchamorro.bancopichincha.service.AccountService;
import com.kevinchamorro.bancopichincha.service.CustomerService;
import com.kevinchamorro.bancopichincha.service.ReportService;
import com.kevinchamorro.bancopichincha.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final TransactionService transactionService;
    private final AccountService accountService;
    private final CustomerService customerService;

    @Override
    public Flux<ReportTransactionResponseDto> findTransactionByFilters(
            LocalDate dateFrom, LocalDate dateTo, String customerNumber
    ) {
        return transactionService.findAll()

                // FILTER BY DATE FROM
                .filter(transactionResponseDto -> dateFrom != null ?
                        transactionResponseDto.getTransactionDate().toLocalDate().isAfter(dateFrom)
                                || transactionResponseDto.getTransactionDate().toLocalDate().equals(dateFrom) : Boolean.TRUE)

                // FILTER BY DATE TO
                .filter(transactionResponseDto -> dateTo != null ?
                        transactionResponseDto.getTransactionDate().toLocalDate().isBefore(dateTo)
                                || transactionResponseDto.getTransactionDate().toLocalDate().equals(dateTo) : Boolean.TRUE)

                .flatMap(transactionResponseDto -> accountService.findByAccountNumber(transactionResponseDto.getAccountNumber())
                        .flatMap(accountResponseDto -> customerService.findByDocumentNumber(accountResponseDto.getCustomerDocumentNumber())

                                // FILTER BY CUSTOMER NUMBER
                                .filter(customerResponseDto -> customerNumber != null && !customerNumber.isBlank() ?
                                        customerResponseDto.getCustomerNumber().equals(customerNumber) : Boolean.TRUE)

                                .map(customerResponseDto -> ReportTransactionResponseDto.builder()
                                        .Fecha(transactionResponseDto.getTransactionDate())
                                        .Cliente(customerResponseDto.getName())
                                        .NumeroCuenta(accountResponseDto.getAccountNumber())
                                        .Tipo(accountResponseDto.getAccountType())
                                        .SaldoInicial(transactionResponseDto.getAccountInitialBalance())
                                        .Estado(accountResponseDto.getState())
                                        .Movimiento(transactionResponseDto.getTransactionValue())
                                        .SaldoDisponible(transactionResponseDto.getAccountEndingBalance()).build())
                        )
                );
    }
}
