package com.kevinchamorro.bancopichincha.service.Impl;

import com.kevinchamorro.bancopichincha.dto.ParameterRequestDto;
import com.kevinchamorro.bancopichincha.dto.ParameterResponseDto;
import com.kevinchamorro.bancopichincha.entity.ParameterEntity;
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
        return parameterRepo.findAll().map(parameterMapper::toParameterResponseDto);
    }

    @Override
    public Mono<ParameterResponseDto> findByName(String name){
        return parameterRepo.findFirstByName(name)
                .onErrorMap(errorMap -> new RuntimeException("No existe el parametro con el name: " + name))
                .map(parameterMapper::toParameterResponseDto);
    }

    @Override
    public Mono<ParameterResponseDto> save(Mono<ParameterRequestDto> parameterRequestDto) {
        return Mono.error(new RuntimeException("No implementado"));
    }

    @Override
    public Mono<ParameterResponseDto> update(Long id, Mono<ParameterRequestDto> parameterRequestDto) {
        return Mono.error(new RuntimeException("No implementado"));
    }

    @Override
    public Mono<ParameterResponseDto> patch(Long id, Mono<ParameterRequestDto> parameterRequestDto) {
        return Mono.error(new RuntimeException("No implementado"));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return Mono.error(new RuntimeException("No implementado"));
    }

    public Mono<String> nextValueByName(String name){

        return parameterRepo.findFirstByName(name)
                .onErrorMap(errorMap -> new RuntimeException("El parametro con el name: " + name + " no existe"))
                .flatMap(parameterEntity -> {
                    parameterEntity.setValue(String.valueOf(Long.parseLong(parameterEntity.getValue()) + 1L));
                    return parameterRepo.save(parameterEntity)
                            .onErrorMap(errorMap -> new RuntimeException("Error al actualizar el siguiente valor al parametro " + name));
                })
                .map(ParameterEntity::getValue);
    }
}
