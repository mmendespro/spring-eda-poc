package net.local.poc.orders.service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import net.local.poc.orders.service.model.Order;

public interface OrderRepository extends ReactiveMongoRepository<Order, String> {
}
