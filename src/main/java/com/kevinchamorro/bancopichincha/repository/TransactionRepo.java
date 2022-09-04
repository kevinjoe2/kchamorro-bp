package com.kevinchamorro.bancopichincha.repository;

import com.kevinchamorro.bancopichincha.entity.TransactionEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TransactionRepo extends ReactiveCrudRepository<TransactionEntity, Long> {
    Flux<TransactionEntity> findByAccountId(Long accountId);
}
