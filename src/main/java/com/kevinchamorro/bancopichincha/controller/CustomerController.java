package com.kevinchamorro.bancopichincha.controller;

import com.kevinchamorro.bancopichincha.dto.CustomerRequestDto;
import com.kevinchamorro.bancopichincha.dto.CustomerResponseDto;
import com.kevinchamorro.bancopichincha.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public Mono<ResponseEntity<Flux<CustomerResponseDto>>> getAll(){
        return Mono.just(ResponseEntity.ok(customerService.findAll()));
    }

    @GetMapping("findByDocumentNumber")
    public Mono<ResponseEntity<Mono<CustomerResponseDto>>> getByDocumentNumber(
            @RequestParam("documentNumber") String documentNumber
    ){
        return Mono.just(ResponseEntity.ok(customerService.findByDocumentNumber(documentNumber)));
    }

    @PostMapping
    public Mono<ResponseEntity<Mono<CustomerResponseDto>>> post(
            @RequestBody Mono<CustomerRequestDto> customerRequestDtoMono
    ){
        return Mono.just(ResponseEntity.ok(customerService.save(customerRequestDtoMono)));
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<Mono<CustomerResponseDto>>> put(
            @PathVariable("id") Long id,
            @RequestBody Mono<CustomerRequestDto> customerRequestDtoMono
    ){
        return Mono.just(ResponseEntity.ok(customerService.update(id, customerRequestDtoMono)));
    }

    @PatchMapping("{id}")
    public Mono<ResponseEntity<Mono<CustomerResponseDto>>> patch(
            @PathVariable("id") Long id,
            @RequestBody Mono<CustomerRequestDto> customerRequestDtoMono
    ){
        return Mono.just(ResponseEntity.ok(customerService.patch(id, customerRequestDtoMono)));
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Mono<Void>>> delete(
            @PathVariable("id") Long id
    ){
        return Mono.just(ResponseEntity.ok(customerService.delete(id)));
    }
}
