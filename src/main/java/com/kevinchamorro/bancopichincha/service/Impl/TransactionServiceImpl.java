package com.kevinchamorro.bancopichincha.service.Impl;

import com.kevinchamorro.bancopichincha.dto.AccountResponseDto;
import com.kevinchamorro.bancopichincha.dto.TransactionRequestDto;
import com.kevinchamorro.bancopichincha.dto.TransactionResponseDto;
import com.kevinchamorro.bancopichincha.entity.AccountEntity;
import com.kevinchamorro.bancopichincha.entity.TransactionEntity;
import com.kevinchamorro.bancopichincha.mapper.TransactionMapper;
import com.kevinchamorro.bancopichincha.repository.AccountRepo;
import com.kevinchamorro.bancopichincha.repository.CustomerRepo;
import com.kevinchamorro.bancopichincha.repository.ParameterRepo;
import com.kevinchamorro.bancopichincha.repository.TransactionRepo;
import com.kevinchamorro.bancopichincha.service.AccountService;
import com.kevinchamorro.bancopichincha.service.ParameterService;
import com.kevinchamorro.bancopichincha.service.TransactionService;
import com.kevinchamorro.bancopichincha.util.TransactionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepo transactionRepo;
    private final AccountService accountService;
    private final ParameterService parameterService;
    private final TransactionMapper transactionMapper;

    @Override
    public Flux<TransactionResponseDto> findAll() {
        return transactionRepo.findAll().map(transactionMapper::toTransactionResponseDto);
    }

    @Override
    @Transactional
    public Mono<TransactionResponseDto> save(Mono<TransactionRequestDto> transactionRequestDto){
        return transactionRequestDto
                .flatMap(this::saveTransaction)
                .map(transactionMapper::toTransactionResponseDto);
    }

    @Override
    public Mono<TransactionResponseDto> update(Long id, Mono<TransactionRequestDto> transactionRequestDto) {
        return Mono.error(new RuntimeException("No implementado"));
    }

    @Override
    public Mono<TransactionResponseDto> patch(Long id, Mono<TransactionRequestDto> transactionRequestDto) {
        return Mono.error(new RuntimeException("No implementado"));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return null;
    }

    private Mono<TransactionEntity> saveTransaction(TransactionRequestDto transactionRequestDto){
        return accountService.findByAccountNumber(transactionRequestDto.getAccountNumber())
                .flatMap(accountResponseDto -> this.validateTransaction(transactionRequestDto, accountResponseDto).flatMap(limitExceeded -> {
                    if (!limitExceeded) {
                        return parameterService.nextValueByName("NumeroTransaccion")
                                .flatMap(valueParameter -> accountService.findAccountIdByAccountNumber(transactionRequestDto.getAccountNumber())
                                        .map(accountId -> TransactionEntity.builder()
                                                .transactionDate(LocalDateTime.now())
                                                .transactionNumber(valueParameter)
                                                .transactionType(TransactionUtil.transactionTypeByTransactionValue(transactionRequestDto.getTransactionValue()))
                                                .transactionValue(transactionRequestDto.getTransactionValue())
                                                .accountInitialBalance(accountResponseDto.getBalance())
                                                .accountEndingBalance(accountResponseDto.getBalance().add(transactionRequestDto.getTransactionValue()))
                                                .accountId(accountId).build()))
                                .flatMap(newTransactionEntity -> transactionRepo.save(newTransactionEntity)
                                        .onErrorMap(errorMap -> new RuntimeException("Error al guardar la transaccion"))
                                        .flatMap(transactionEntity -> accountService.patchAccountByTransaction(transactionEntity)
                                                .thenReturn(transactionEntity)));
                    } else {
                        return Mono.error(new RuntimeException("Cupo diario Excedido"));
                    }
                }));
    }

    private Mono<Boolean> validateTransaction(
            TransactionRequestDto transactionRequestDto, AccountResponseDto accountResponseDto
    ){
        LocalDate localDate = LocalDate.now();

        if (transactionRequestDto.getTransactionValue().compareTo(BigDecimal.ZERO) == 0){
            throw new RuntimeException("Indique un valor para la trasaccion");
        }
        if (transactionRequestDto.getTransactionValue().compareTo(BigDecimal.ZERO) < 0){
            if (accountResponseDto.getBalance().compareTo(BigDecimal.ZERO) == 0
                    || accountResponseDto.getBalance().compareTo(transactionRequestDto.getTransactionValue().abs())  < 0
            ){
                throw new RuntimeException("Saldo no disponible");
            }
        }

        return transactionRepo.findByAccountNumber(accountResponseDto.getAccountNumber())
                .filter(transaction -> transaction.getTransactionDate().getYear() == localDate.getYear()
                        && transaction.getTransactionDate().getMonthValue() == localDate.getMonthValue()
                        && transaction.getTransactionDate().getDayOfMonth() == localDate.getDayOfMonth())
                .map(TransactionEntity::getTransactionValue)
                .reduce(BigDecimal::add)
                .flatMap(sum -> parameterService.findByName("LimiteCupoDiarioRetiro")
                        .switchIfEmpty(Mono.error(new RuntimeException("El parametro LimiteCupoDiarioRetiro no existe")))
                        .map(parameter -> transactionRequestDto.getTransactionValue().add(sum).doubleValue() > Integer.parseInt(parameter.getValue()))
                )
                .switchIfEmpty(Mono.just(Boolean.FALSE));
    }
}
