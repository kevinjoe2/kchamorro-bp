package com.kevinchamorro.bancopichincha.service.Impl;

import com.kevinchamorro.bancopichincha.dto.AccountRequestDto;
import com.kevinchamorro.bancopichincha.dto.AccountResponseDto;
import com.kevinchamorro.bancopichincha.entity.AccountEntity;
import com.kevinchamorro.bancopichincha.entity.TransactionEntity;
import com.kevinchamorro.bancopichincha.mapper.AccountMapper;
import com.kevinchamorro.bancopichincha.mapper.PatchGeneralMapper;
import com.kevinchamorro.bancopichincha.repository.AccountRepo;
import com.kevinchamorro.bancopichincha.repository.CustomerRepo;
import com.kevinchamorro.bancopichincha.repository.ParameterRepo;
import com.kevinchamorro.bancopichincha.service.AccountService;
import com.kevinchamorro.bancopichincha.service.CustomerService;
import com.kevinchamorro.bancopichincha.service.ParameterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;
    private final CustomerService customerService;
    private final ParameterService parameterService;
    private final AccountMapper accountMapper;
    private final PatchGeneralMapper patchGeneralMapper;

    @Override
    public Flux<AccountResponseDto> findAll() {
        return accountRepo.findAll()
                .map(accountMapper::toAccountResponseDto);
    }

    public Mono<Long> findAccountIdByAccountNumber(String accountNumber){
        return accountRepo.findFirstByAccountNumber(accountNumber)
                .switchIfEmpty(Mono.error(new RuntimeException("La cuenta con el numero " + accountNumber + " no existe")))
                .map(AccountEntity::getId);
    }

    @Override
    public Mono<AccountResponseDto> findByAccountNumber(String accountNumber) {
        return accountRepo.findFirstByAccountNumber(accountNumber)
                .switchIfEmpty(Mono.error(new RuntimeException("La cuenta con el numero " + accountNumber + " no existe")))
                .map(accountMapper::toAccountResponseDto);
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
    public Mono<AccountResponseDto> patchAccountByTransaction(TransactionEntity transactionEntity){
        return accountRepo.findById(transactionEntity.getAccountId())
                .switchIfEmpty(Mono.error(new RuntimeException("La cuenta con el id " + transactionEntity.getAccountId() + " no existe")))
                .flatMap(entity -> {
                    entity.setBalance(entity.getBalance().add(transactionEntity.getTransactionValue()));
                    return accountRepo.save(entity)
                            .onErrorMap(errorMap -> new RuntimeException("Error al actualizar el balance de la cuenta"));
                })
                .map(accountMapper::toAccountResponseDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return Mono.error(new RuntimeException("No implementado"));
    }

    private Mono<AccountEntity> saveAccount(AccountRequestDto requestDto) {
        return customerService.findByDocumentNumber(requestDto.getCustomerDocumentNumber())
                .flatMap(customerEntity -> parameterService.nextValueByName("NumeroCuenta")
                        .flatMap(parameterValue -> customerService.findCustomerIdByDocumentNumber(requestDto.getCustomerDocumentNumber())
                                .map(customerId -> AccountEntity.builder()
                                        .accountNumber("CUE" + parameterValue)
                                        .accountType(requestDto.getAccountType())
                                        .balance(BigDecimal.valueOf(0))
                                        .state("ACT")
                                        .customerId(customerId).build()))
                        .flatMap(newAccountEntity -> accountRepo.save(newAccountEntity)
                                .onErrorMap(errorMap -> new RuntimeException("Error al guardar la cuenta"))));
    }
}
