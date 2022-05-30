package com.microservice.controller;

import com.microservice.model.Credit;
import com.microservice.service.CreditService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/credit")
public class CreditController {

    private final CreditService creditService;

    private static final String CREDIT = "credit";

    @GetMapping(value = "/allCredits")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Credit> getAllCredits(){
        System.out.println("Listar todas las tarjetas de credito.");
        return creditService.getAllCredits();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Credit> getCreditById(@PathVariable String id){
        System.out.println("Listar tarjeta de credito por ID.");
        return creditService.getByIdCredit(id);
    }

    @PostMapping(value = "/create")
    @CircuitBreaker(name = CREDIT, fallbackMethod = "credit")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Credit> createCredit(@RequestBody Credit credit){
        System.out.println("Tarjeta de credito creada con Éxito");
        return creditService.createCredit(credit);
    }

}
