package com.kevinchamorro.bancopichincha.controller;

import com.kevinchamorro.bancopichincha.dto.AccountRequestDto;
import com.kevinchamorro.bancopichincha.dto.AccountResponseDto;
import com.kevinchamorro.bancopichincha.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public Mono<ResponseEntity<Flux<AccountResponseDto>>> getAll(){
        return Mono.just(ResponseEntity.ok(accountService.findAll()));
    }

    @GetMapping("findByAccountNumber")
    public Mono<ResponseEntity<Mono<AccountResponseDto>>> getByAccountNumber(
            @RequestParam("accountNumber") String accountNumber
    ){
        return Mono.just(ResponseEntity.ok(accountService.findByAccountNumber(accountNumber)));
    }

    @PostMapping
    public Mono<ResponseEntity<Mono<AccountResponseDto>>> post(
            @RequestBody Mono<AccountRequestDto> accountRequestDtoMono
    ){
        return Mono.just(ResponseEntity.ok(accountService.save(accountRequestDtoMono)));
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<Mono<AccountResponseDto>>> put(
            @PathVariable("id") Long id, @RequestBody Mono<AccountRequestDto> accountRequestDtoMono
    ){
        return Mono.just(ResponseEntity.ok(accountService.update(id, accountRequestDtoMono)));
    }

    @PatchMapping("{id}")
    public Mono<ResponseEntity<Mono<AccountResponseDto>>> patch(
            @PathVariable("id") Long id, @RequestBody Mono<AccountRequestDto> accountRequestDtoMono
    ){
        return Mono.just(ResponseEntity.ok(accountService.patch(id, accountRequestDtoMono)));
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Mono<Void>>> delete(@PathVariable("id") Long id){
        return Mono.just(ResponseEntity.ok(accountService.delete(id)));
    }
}
