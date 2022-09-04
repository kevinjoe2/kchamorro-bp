package com.kevinchamorro.bancopichincha.service.Impl;

import com.kevinchamorro.bancopichincha.dto.CustomerRequestDto;
import com.kevinchamorro.bancopichincha.dto.CustomerResponseDto;
import com.kevinchamorro.bancopichincha.mapper.CustomerMapper;
import com.kevinchamorro.bancopichincha.repository.CustomerRepo;
import com.kevinchamorro.bancopichincha.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;
    private final CustomerMapper customerMapper;

    @Override
    public Flux<CustomerResponseDto> findAll(){
        return customerRepo.findAll().map(customerMapper::toCustomerDto);
    }

    @Override
    public Mono<CustomerResponseDto> findByDocumentNumber(String documentNumber) {
        return null;
    }

    @Override
    public Mono<CustomerResponseDto> save(Mono<CustomerRequestDto> customerRequestDtoMono) {
        return null;
    }

    @Override
    public Mono<CustomerResponseDto> update(Long id, Mono<CustomerRequestDto> customerRequestDtoMono) {
        return null;
    }

    @Override
    public Mono<CustomerResponseDto> patch(Long id, Mono<CustomerRequestDto> customerRequestDtoMono) {
        return null;
    }

    @Override
    public Mono<Void> delete(Long id) {
        return null;
    }

}
