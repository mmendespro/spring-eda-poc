package net.local.poc.warehouse.service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import net.local.poc.warehouse.service.model.Shipment;

public interface ShipmentRepository extends ReactiveMongoRepository<Shipment, String> {
}
