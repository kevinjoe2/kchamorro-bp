package com.kevinchamorro.bancopichincha.repository;

import com.kevinchamorro.bancopichincha.entity.TransactionEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TransactionRepo extends ReactiveCrudRepository<TransactionEntity, Long> {

    Flux<TransactionEntity> findByAccountId(Long accountId);

    @Query("SELECT tra.* FROM accounts acc " +
            "INNER JOIN transactions tra on acc.id = tra.account_id " +
            "WHERE acc.account_number = :accountNumber")
    Flux<TransactionEntity> findByAccountNumber(String accountNumber);

}
