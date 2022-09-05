package com.kevinchamorro.bancopichincha.service;

import com.kevinchamorro.bancopichincha.dto.ParameterRequestDto;
import com.kevinchamorro.bancopichincha.dto.ParameterResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ParameterService {
    Flux<ParameterResponseDto> findAll();
    Mono<ParameterResponseDto> findByName(String name);
    Mono<ParameterResponseDto> save(Mono<ParameterRequestDto> parameterRequestDto);
    Mono<ParameterResponseDto> update(Long id, Mono<ParameterRequestDto> parameterRequestDto);
    Mono<ParameterResponseDto> patch(Long id, Mono<ParameterRequestDto> parameterRequestDto);
    Mono<Void> delete(Long id);
    Mono<String> nextValueByName(String name);
}
