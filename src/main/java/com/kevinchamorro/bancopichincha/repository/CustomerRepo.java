package com.kevinchamorro.bancopichincha.repository;

import com.kevinchamorro.bancopichincha.entity.CustomerEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CustomerRepo extends ReactiveCrudRepository<CustomerEntity, Long> {
    Mono<CustomerEntity> findFirstByDocumentNumber(String documentNumber);
}
