package com.kevinchamorro.bancopichincha.controller;

import com.kevinchamorro.bancopichincha.dto.ParameterRequestDto;
import com.kevinchamorro.bancopichincha.dto.ParameterResponseDto;
import com.kevinchamorro.bancopichincha.service.ParameterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("parameter")
@RequiredArgsConstructor
public class ParameterController {
    private final ParameterService parameterService;

    @GetMapping
    public Mono<ResponseEntity<Flux<ParameterResponseDto>>> getAll(){
        return Mono.just(ResponseEntity.ok(parameterService.findAll()));
    }

    @GetMapping("findByName")
    public Mono<ResponseEntity<Mono<ParameterResponseDto>>> getByDocumentNumber(
            @RequestParam("name") String name
    ){
        return Mono.just(ResponseEntity.ok(parameterService.findByName(name)));
    }

    @PostMapping
    public Mono<ResponseEntity<Mono<ParameterResponseDto>>> post(
            @RequestBody Mono<ParameterRequestDto> parameterRequestDto
    ){
        return Mono.just(ResponseEntity.ok(parameterService.save(parameterRequestDto)));
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<Mono<ParameterResponseDto>>> post(
            @PathVariable("id") Long id,
            @RequestBody Mono<ParameterRequestDto> parameterRequestDto
    ){
        return Mono.just(ResponseEntity.ok(parameterService.update(id, parameterRequestDto)));
    }

    @PatchMapping("{id}")
    public Mono<ResponseEntity<Mono<ParameterResponseDto>>> patch(
            @PathVariable("id") Long id,
            @RequestBody Mono<ParameterRequestDto> parameterRequestDto
    ){
        return Mono.just(ResponseEntity.ok(parameterService.patch(id, parameterRequestDto)));
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Mono<Void>>> delete(
            @PathVariable("id") Long id
    ){
        return Mono.just(ResponseEntity.ok(parameterService.delete(id)));
    }
}
