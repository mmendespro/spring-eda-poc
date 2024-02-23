package net.local.poc.customer.service.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.local.poc.customer.service.handlers.exceptions.CustomerNotFound;
import net.local.poc.customer.service.repository.CustomerRepository;
import net.local.poc.customer.service.model.Customer;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository customerRepository;

	public Mono<Customer> get(String id) {
		return customerRepository.findById(id).switchIfEmpty(Mono.error(new CustomerNotFound(id)));
	}

	public Mono<Customer> create(Customer customer) {
		return customerRepository.save(customer);
	}
}
