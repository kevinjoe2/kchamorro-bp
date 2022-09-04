package com.kevinchamorro.bancopichincha.repository;

import com.kevinchamorro.bancopichincha.entity.ParameterEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ParameterRepo extends ReactiveCrudRepository<ParameterEntity, Long> {
    Mono<ParameterEntity> findFirstByName(String name);
}
