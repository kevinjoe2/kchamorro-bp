package com.kevinchamorro.bancopichincha.service;

import com.kevinchamorro.bancopichincha.dto.AccountRequestDto;
import com.kevinchamorro.bancopichincha.dto.AccountResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Flux<AccountResponseDto> findAll();
    Mono<AccountResponseDto> findByAccountNumber(String accountNumber);
    Mono<AccountResponseDto> save(Mono<AccountRequestDto> accountRequestDto);
    Mono<AccountResponseDto> update(Long id, Mono<AccountRequestDto> accountRequestDto);
    Mono<AccountResponseDto> patch(Long id, Mono<AccountRequestDto> accountRequestDto);
    Mono<Void> delete(Long id);
}
