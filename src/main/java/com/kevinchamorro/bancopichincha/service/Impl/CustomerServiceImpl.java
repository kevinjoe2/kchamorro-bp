package com.kevinchamorro.bancopichincha.service.Impl;

import com.kevinchamorro.bancopichincha.dto.CustomerRequestDto;
import com.kevinchamorro.bancopichincha.dto.CustomerResponseDto;
import com.kevinchamorro.bancopichincha.entity.CustomerEntity;
import com.kevinchamorro.bancopichincha.mapper.CustomerMapper;
import com.kevinchamorro.bancopichincha.mapper.PatchGeneralMapper;
import com.kevinchamorro.bancopichincha.repository.CustomerRepo;
import com.kevinchamorro.bancopichincha.service.CustomerService;
import com.kevinchamorro.bancopichincha.service.ParameterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;
    private final ParameterService parameterService;
    private final CustomerMapper customerMapper;
    private final PatchGeneralMapper patchGeneralMapper;

    @Override
    public Flux<CustomerResponseDto> findAll(){
        return customerRepo.findAll()
                .map(customerMapper::toCustomerDto);
    }

    @Override
    public Mono<CustomerResponseDto> findById(Long id) {
        return customerRepo.findById(id)
                .map(customerMapper::toCustomerDto);
    }

    @Override
    public Mono<CustomerResponseDto> findByDocumentNumber(String documentNumber) {
        return customerRepo.findFirstByDocumentNumber(documentNumber)
                .map(customerMapper::toCustomerDto);
    }

    @Override
    public Mono<Long> findCustomerIdByDocumentNumber(String documentNumber) {
        return customerRepo.findFirstByDocumentNumber(documentNumber)
                .map(CustomerEntity::getId);
    }

    @Override
    public Mono<CustomerResponseDto> save(Mono<CustomerRequestDto> customerRequestDtoMono) {
        return customerRequestDtoMono
                .flatMap(customerRequestDto -> parameterService.nextValueByName("NumeroCliente")
                        .map(parameterValue -> new  CustomerEntity(customerRequestDto.getName(),
                                customerRequestDto.getGender(),
                                customerRequestDto.getDateBirth(),
                                customerRequestDto.getDocumentNumber(),
                                customerRequestDto.getAddress(),
                                customerRequestDto.getPhone(),
                                customerRequestDto.getPassword(),
                                parameterValue,"ACT")))
                .flatMap(customerRepo::save)
                .onErrorMap(errorMap -> errorMap instanceof DataIntegrityViolationException ? new RuntimeException("Ya existe un cliente con dicha identificacion") : new RuntimeException("Ocurrio un error inesperado al guardar el cliente"))
                .map(customerMapper::toCustomerDto);
    }

    @Override
    public Mono<CustomerResponseDto> update(Long id, Mono<CustomerRequestDto> customerRequestDtoMono) {
        return Mono.error(new RuntimeException("No implementado"));
    }

    @Override
    public Mono<CustomerResponseDto> patch(Long id, Mono<CustomerRequestDto> customerRequestDtoMono) {
        return customerRequestDtoMono.flatMap(customerRequestDto -> customerRepo.findById(id)
                .map(customerEntity -> {
                    patchGeneralMapper.patchCustomerEntityFromFto(customerRequestDto, customerEntity);
                    return customerEntity;
                }))
                .flatMap(customerRepo::save)
                .map(customerMapper::toCustomerDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return Mono.error(new RuntimeException("No implementado"));
    }

}
