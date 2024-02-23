package net.local.poc.warehouse.service.model;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@With
@Value
@Builder
@Document
@RequiredArgsConstructor
public class Shipment {
	@Id
	private final String id;
	private final String orderId;
	private final String customerId;
	private final Address shippingAddress;
	private final Instant dateCreated;
	private final Instant dateShipped;
}
