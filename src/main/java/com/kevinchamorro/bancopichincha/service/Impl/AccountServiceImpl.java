package com.kevinchamorro.bancopichincha.service.Impl;

import com.kevinchamorro.bancopichincha.dto.AccountRequestDto;
import com.kevinchamorro.bancopichincha.dto.AccountResponseDto;
import com.kevinchamorro.bancopichincha.mapper.AccountMapper;
import com.kevinchamorro.bancopichincha.mapper.PatchGeneralMapper;
import com.kevinchamorro.bancopichincha.repository.AccountRepo;
import com.kevinchamorro.bancopichincha.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;
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
        return accountRequestDto.flatMap(requestDto -> accountRepo.save(accountMapper.toAccountEntity(requestDto)))
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
}
