package com.kevinchamorro.bancopichincha.service.Impl;

import com.kevinchamorro.bancopichincha.dto.AccountRequestDto;
import com.kevinchamorro.bancopichincha.dto.AccountResponseDto;
import com.kevinchamorro.bancopichincha.entity.AccountEntity;
import com.kevinchamorro.bancopichincha.mapper.AccountMapper;
import com.kevinchamorro.bancopichincha.mapper.PatchGeneralMapper;
import com.kevinchamorro.bancopichincha.repository.AccountRepo;
import com.kevinchamorro.bancopichincha.repository.CustomerRepo;
import com.kevinchamorro.bancopichincha.repository.ParameterRepo;
import com.kevinchamorro.bancopichincha.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final CustomerRepo customerRepo;
    private final AccountRepo accountRepo;
    private final ParameterRepo parameterRepo;
    private final AccountMapper accountMapper;
    private final PatchGeneralMapper patchGeneralMapper;

    @Override
    public Flux<AccountResponseDto> findAll() {
        return accountRepo.findAll().map(accountMapper::toAccountResponseDto);
    }

    @Override
    public Mono<AccountResponseDto> findByAccountNumber(String accountNumber) {
        return accountRepo.findFirstByAccountNumber(accountNumber).map(accountMapper::toAccountResponseDto);
    }

    @Override
    public Mono<AccountResponseDto> save(Mono<AccountRequestDto> accountRequestDto) {
        return accountRequestDto
                .flatMap(this::saveAccount)
                .map(accountMapper::toAccountResponseDto);
    }

    @Override
    public Mono<AccountResponseDto> update(Long id, Mono<AccountRequestDto> accountRequestDto) {
        return accountRequestDto.flatMap(requestDto -> accountRepo.findById(id)
                        .flatMap(accountEntity -> {
                            accountMapper.putAccountEntityFromDto(requestDto, accountEntity);
                            return accountRepo.save(accountEntity);
                        }))
                .map(accountMapper::toAccountResponseDto);
    }

    @Override
    public Mono<AccountResponseDto> patch(Long id, Mono<AccountRequestDto> accountRequestDto) {
        return accountRequestDto.flatMap(requestDto -> accountRepo.findById(id)
                        .flatMap(accountEntity -> {
                            patchGeneralMapper.patchAccountEntityFromFto(requestDto, accountEntity);
                            return accountRepo.save(accountEntity);
                        }))
                .map(accountMapper::toAccountResponseDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return accountRepo.deleteById(id);
    }

    private Mono<AccountEntity> saveAccount(AccountRequestDto requestDto) {
        return customerRepo.findFirstByDocumentNumber(requestDto.getCustomerDocumentNumber())
                .switchIfEmpty(Mono.error(new RuntimeException("Cliente no existe")))
                .flatMap(customerEntity -> parameterRepo.findFirstByName("NumeroCuenta")
                        .switchIfEmpty(Mono.error(new RuntimeException("El parametro NumeroCuenta")))
                        .flatMap(parameterEntity -> {
                            parameterEntity.setValue(String.valueOf(Long.parseLong(parameterEntity.getValue()) + 1L));
                            return parameterRepo.save(parameterEntity)
                                    .onErrorMap(errorMap -> new RuntimeException("Error al guardar el parametro"))
                                    .flatMap(parameterEntityNew -> {
                                        AccountEntity accountEntity =
                                                AccountEntity
                                                        .builder()
                                                        .accountNumber("CUE" + parameterEntity.getValue())
                                                        .accountType(requestDto.getAccountType())
                                                        .balance(BigDecimal.valueOf(0))
                                                        .state("ACT")
                                                        .customerId(customerEntity.getId())
                                                        .build();
                                        return accountRepo.save(accountEntity)
                                                .onErrorMap(errorMap -> new RuntimeException("Error al guardar la cuenta"));
                                    });
                        }));
    }
}
