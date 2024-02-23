package net.local.poc.warehouse.service.model;

import java.time.Instant;
import java.util.List;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
public class Order {

	private final String id;
	private final String customerId;
	private final List<OrderItem> orderItems;
	private final Address shippingAddress;

  	public Shipment shipOrder() {
		return Shipment.builder()
					   .orderId(id)
					   .customerId(customerId)
					   .shippingAddress(shippingAddress)
					   .dateCreated(Instant.now()).build();
  	}
}
