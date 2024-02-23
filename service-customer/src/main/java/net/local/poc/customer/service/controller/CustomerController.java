package net.local.poc.customer.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.local.poc.customer.service.model.Customer;
import net.local.poc.customer.service.service.CustomerService;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("v1/customers")
public class CustomerController {
    
    private final CustomerService customerService;

    @GetMapping("/{id}")
    public Mono<Customer> getOne(@PathVariable String id) {
        return customerService.get(id);
    }

    @PostMapping
    public Mono<Customer> postMethodName(@RequestBody Customer customer) {
        return customerService.create(customer);
    }
    
}
