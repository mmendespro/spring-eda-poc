package net.local.poc.warehouse.service.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.local.poc.warehouse.service.model.Order;
import net.local.poc.warehouse.service.model.Shipment;
import net.local.poc.warehouse.service.repository.ShipmentRepository;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ShipmentService {

	private final ShipmentRepository shipmentRepository;

	public Mono<Shipment> prepare(Order order) {
		return shipmentRepository.save(order.shipOrder());
	}
}
