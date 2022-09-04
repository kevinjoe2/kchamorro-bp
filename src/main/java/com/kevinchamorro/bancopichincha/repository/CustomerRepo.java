package com.kevinchamorro.bancopichincha.repository;

import com.kevinchamorro.bancopichincha.entity.CustomerEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CustomerRepo extends ReactiveCrudRepository<CustomerEntity, Long> {
}
