package net.local.poc.warehouse.service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import net.local.poc.warehouse.service.model.StockLine;

public interface StockLineRepository extends ReactiveMongoRepository<StockLine, String> {
}
