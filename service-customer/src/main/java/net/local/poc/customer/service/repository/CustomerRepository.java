package net.local.poc.customer.service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import net.local.poc.customer.service.model.Customer;

public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
}
