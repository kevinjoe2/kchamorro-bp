package com.kevinchamorro.bancopichincha.service.Impl;

import com.kevinchamorro.bancopichincha.dto.TransactionRequestDto;
import com.kevinchamorro.bancopichincha.dto.TransactionResponseDto;
import com.kevinchamorro.bancopichincha.entity.AccountEntity;
import com.kevinchamorro.bancopichincha.entity.TransactionEntity;
import com.kevinchamorro.bancopichincha.mapper.TransactionMapper;
import com.kevinchamorro.bancopichincha.repository.AccountRepo;
import com.kevinchamorro.bancopichincha.repository.CustomerRepo;
import com.kevinchamorro.bancopichincha.repository.ParameterRepo;
import com.kevinchamorro.bancopichincha.repository.TransactionRepo;
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
    private final AccountRepo accountRepo;
    private final TransactionMapper transactionMapper;
    private final ParameterRepo parameterRepo;

    @Override
    public Flux<TransactionResponseDto> findAll() {
        return null;
    }

    @Override
    @Transactional
    public Mono<TransactionResponseDto> save(Mono<TransactionRequestDto> transactionRequestDto){
        return transactionRequestDto
                .flatMap(req -> accountRepo.findFirstByAccountNumber(req.getAccountNumber())
                        .switchIfEmpty(Mono.error(new RuntimeException("La cuenta no existe")))
                        .flatMap(accountEntity -> this.saveTransactionAndAccount(req, accountEntity))
                )
                .map(transactionMapper::toTransactionResponseDto);
    }

    @Override
    public Mono<TransactionResponseDto> update(Long id, Mono<TransactionRequestDto> transactionRequestDto) {
        return null;
    }

    @Override
    public Mono<TransactionResponseDto> patch(Long id, Mono<TransactionRequestDto> transactionRequestDto) {
        return null;
    }

    @Override
    public Mono<Void> delete(Long id) {
        return null;
    }

    private Mono<TransactionEntity> saveTransactionAndAccount(
            TransactionRequestDto transactionRequestDto,
            AccountEntity accountEntity
    ){

        if (transactionRequestDto.getTransactionValue().compareTo(BigDecimal.ZERO) == 0){
            throw new RuntimeException("Indique un valor para la trasaccion");
        }
        if (transactionRequestDto.getTransactionValue().compareTo(BigDecimal.ZERO) < 0){
            if (accountEntity.getBalance().compareTo(BigDecimal.ZERO) == 0
                    || accountEntity.getBalance().compareTo(transactionRequestDto.getTransactionValue().abs())  < 0
            ){
                throw new RuntimeException("Saldo no disponible");
            }
        }

        return this.validateLimitTransaction(transactionRequestDto, accountEntity).flatMap(limitExceeded -> {
            if (!limitExceeded){

                TransactionEntity newTransactionEntity = TransactionEntity.builder()
                        .transactionDate(LocalDateTime.now())
                        .transactionType(TransactionUtil.transactionTypeByTransactionValue(transactionRequestDto.getTransactionValue()))
                        .transactionValue(transactionRequestDto.getTransactionValue())
                        .accountInitialBalance(accountEntity.getBalance())
                        .accountEndingBalance(accountEntity.getBalance().add(transactionRequestDto.getTransactionValue()))
                        .accountId(accountEntity.getId())
                        .build();

                accountEntity.setBalance(accountEntity.getBalance().add(transactionRequestDto.getTransactionValue()));

                return transactionRepo
                        .save(newTransactionEntity)
                        .flatMap(transactionEntity -> accountRepo.save(accountEntity).thenReturn(transactionEntity));

            } else {
                return Mono.error(new RuntimeException("Cupo diario Excedido"));
            }
        });
    }

    private Mono<Boolean> validateLimitTransaction(
            TransactionRequestDto transactionRequestDto,
            AccountEntity accountEntity
    ) {
        LocalDate localDate = LocalDate.now();
        return transactionRepo.findByAccountId(accountEntity.getId())
                .filter(transaction -> transaction.getTransactionDate().getYear() == localDate.getYear()
                        && transaction.getTransactionDate().getMonthValue() == localDate.getMonthValue()
                        && transaction.getTransactionDate().getDayOfMonth() == localDate.getDayOfMonth())
                .map(TransactionEntity::getTransactionValue)
                .reduce(BigDecimal::add)
                .flatMap(sum -> parameterRepo.findFirstByName("LimiteCupoDiarioRetiro")
                        .switchIfEmpty(Mono.error(new RuntimeException("El parametro LimiteCupoDiarioRetiro no existe")))
                        .map(parameter -> transactionRequestDto.getTransactionValue().add(sum).doubleValue() > Integer.parseInt(parameter.getValue()))
                )
                .switchIfEmpty(Mono.just(Boolean.FALSE));
    }


}
