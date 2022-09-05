package com.kevinchamorro.bancopichincha.service.Impl;

import com.kevinchamorro.bancopichincha.dto.CustomerResponseDto;
import com.kevinchamorro.bancopichincha.entity.CustomerEntity;
import com.kevinchamorro.bancopichincha.mapper.CustomerMapper;
import com.kevinchamorro.bancopichincha.mapper.PatchGeneralMapper;
import com.kevinchamorro.bancopichincha.repository.CustomerRepo;
import com.kevinchamorro.bancopichincha.service.ParameterService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceImplTest {

    @InjectMocks
    CustomerServiceImpl customerService;

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private ParameterService parameterService;

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private PatchGeneralMapper patchGeneralMapper;

    @Test
    void findAll() {

        CustomerEntity customerEntity = new CustomerEntity(
                "NAME TEST","GENDER TEST",
                LocalDate.now(),"DOCUMENT NUMBER TEST",
                "ADDRESS TEST","PHONE TEST",
                "CUSTOMER NUMBER TEST",
                "PASSWORD TEST","STATE TEST"
        );

        CustomerResponseDto customerResponseDto = CustomerResponseDto.builder()
                .customerNumber(customerEntity.getCustomerNumber()).build();

        Mockito.when(customerRepo.findAll())
                .thenReturn(Flux.just(customerEntity));

        Mockito.when(customerMapper.toCustomerDto(Mockito.any()))
                        .thenReturn(customerResponseDto);

        StepVerifier.create(customerService.findAll())
                .consumeNextWith(aa -> assertEquals(aa.getCustomerNumber(), customerEntity.getCustomerNumber()))
                .verifyComplete();
    }

    @Test
    void findById() {

        CustomerEntity customerEntity = new CustomerEntity(
                "NAME TEST","GENDER TEST",
                LocalDate.now(),"DOCUMENT NUMBER TEST",
                "ADDRESS TEST","PHONE TEST",
                "CUSTOMER NUMBER TEST",
                "PASSWORD TEST","STATE TEST"
        );

        CustomerResponseDto customerResponseDto = CustomerResponseDto.builder()
                .customerNumber(customerEntity.getCustomerNumber()).build();

        Mockito.when(customerRepo.findById(Mockito.anyLong()))
                .thenReturn(Mono.just(customerEntity));

        Mockito.when(customerMapper.toCustomerDto(Mockito.any()))
                .thenReturn(customerResponseDto);

        StepVerifier.create(customerService.findById(1L))
                .consumeNextWith(aa -> assertEquals(aa.getCustomerNumber(), customerEntity.getCustomerNumber()))
                .verifyComplete();
    }
}