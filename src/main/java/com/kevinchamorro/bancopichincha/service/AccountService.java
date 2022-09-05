package com.kevinchamorro.bancopichincha.service;

import com.kevinchamorro.bancopichincha.dto.AccountRequestDto;
import com.kevinchamorro.bancopichincha.dto.AccountResponseDto;
import com.kevinchamorro.bancopichincha.entity.TransactionEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Flux<AccountResponseDto> findAll();
    Mono<Long> findAccountIdByAccountNumber(String accountNumber);
    Mono<AccountResponseDto> findByAccountNumber(String accountNumber);
    Mono<AccountResponseDto> save(Mono<AccountRequestDto> accountRequestDto);
    Mono<AccountResponseDto> update(Long id, Mono<AccountRequestDto> accountRequestDto);
    Mono<AccountResponseDto> patch(Long id, Mono<AccountRequestDto> accountRequestDto);
    Mono<AccountResponseDto> patchAccountByTransaction(TransactionEntity transactionEntity);
    Mono<Void> delete(Long id);
}
