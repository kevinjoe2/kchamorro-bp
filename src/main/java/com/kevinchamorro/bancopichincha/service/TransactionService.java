package com.kevinchamorro.bancopichincha.service;

import com.kevinchamorro.bancopichincha.dto.TransactionRequestDto;
import com.kevinchamorro.bancopichincha.dto.TransactionResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {
    Flux<TransactionResponseDto> findAll();
    Mono<TransactionResponseDto> save(Mono<TransactionRequestDto> transactionRequestDto);
    Mono<TransactionResponseDto> update(Long id, Mono<TransactionRequestDto> transactionRequestDto);
    Mono<TransactionResponseDto> patch(Long id, Mono<TransactionRequestDto> transactionRequestDto);
    Mono<Void> delete(Long id);
}
