package com.kevinchamorro.bancopichincha.service.Impl;

import com.kevinchamorro.bancopichincha.dto.ParameterRequestDto;
import com.kevinchamorro.bancopichincha.dto.ParameterResponseDto;
import com.kevinchamorro.bancopichincha.mapper.ParameterMapper;
import com.kevinchamorro.bancopichincha.repository.ParameterRepo;
import com.kevinchamorro.bancopichincha.service.ParameterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ParameterServiceImpl implements ParameterService {

    private final ParameterRepo parameterRepo;
    private final ParameterMapper parameterMapper;

    @Override
    public Flux<ParameterResponseDto> findAll() {
        return null;
    }

    @Override
    public Mono<ParameterResponseDto> findByName(String name){
        return parameterRepo.findFirstByName(name).map(parameterMapper::toParameterResponseDto);
    }

    @Override
    public Mono<ParameterResponseDto> save(Mono<ParameterRequestDto> parameterRequestDto) {
        return null;
    }

    @Override
    public Mono<ParameterResponseDto> update(Long id, Mono<ParameterRequestDto> parameterRequestDto) {
        return null;
    }

    @Override
    public Mono<ParameterResponseDto> patch(Long id, Mono<ParameterRequestDto> parameterRequestDto) {
        return null;
    }

    @Override
    public Mono<Void> delete(Long id) {
        return null;
    }


}
