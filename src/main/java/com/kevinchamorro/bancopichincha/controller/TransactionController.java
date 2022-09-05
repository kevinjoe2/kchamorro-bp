package com.kevinchamorro.bancopichincha.controller;

import com.kevinchamorro.bancopichincha.dto.TransactionRequestDto;
import com.kevinchamorro.bancopichincha.dto.TransactionResponseDto;
import com.kevinchamorro.bancopichincha.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public Mono<ResponseEntity<Flux<TransactionResponseDto>>> getAll(){
        return Mono.just(ResponseEntity.ok(transactionService.findAll()));
    }

    @PostMapping
    public Mono<ResponseEntity<Mono<TransactionResponseDto>>> post(
            @RequestBody Mono<TransactionRequestDto> transactionRequestDto
    ){
        return Mono.just(ResponseEntity.ok(transactionService.save(transactionRequestDto)));
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<Mono<TransactionResponseDto>>> put(
            @PathVariable("id") Long id,
            @RequestBody Mono<TransactionRequestDto> transactionRequestDto
    ){
        return Mono.just(ResponseEntity.ok(transactionService.update(id, transactionRequestDto)));
    }

    @PatchMapping("{id}")
    public Mono<ResponseEntity<Mono<TransactionResponseDto>>> patch(
            @PathVariable("id") Long id,
            @RequestBody Mono<TransactionRequestDto> transactionRequestDto
    ){
        return Mono.just(ResponseEntity.ok(transactionService.patch(id, transactionRequestDto)));
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Mono<Void>>> delete(
            @PathVariable("id") Long id
    ){
        return Mono.just(ResponseEntity.ok(transactionService.delete(id)));
    }
}
