package com.kevinchamorro.bancopichincha.repository;

import com.kevinchamorro.bancopichincha.entity.AccountEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepo extends ReactiveCrudRepository<AccountEntity, Long> {

    Mono<AccountEntity> findFirstByAccountNumber(String accountNumber);

}
