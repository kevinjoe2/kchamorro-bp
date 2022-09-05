package com.kevinchamorro.bancopichincha.service;

import com.kevinchamorro.bancopichincha.dto.CustomerRequestDto;
import com.kevinchamorro.bancopichincha.dto.CustomerResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Flux<CustomerResponseDto> findAll();
    Mono<CustomerResponseDto> findById(Long id);
    Mono<CustomerResponseDto> findByDocumentNumber(String documentNumber);
    Mono<Long> findCustomerIdByDocumentNumber(String documentNumber);
    Mono<CustomerResponseDto> save(Mono<CustomerRequestDto> customerRequestDtoMono);
    Mono<CustomerResponseDto> update(Long id, Mono<CustomerRequestDto> customerRequestDtoMono);
    Mono<CustomerResponseDto> patch(Long id, Mono<CustomerRequestDto> customerRequestDtoMono);
    Mono<Void> delete(Long id);
}
