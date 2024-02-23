package net.local.poc.orders.service.model;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.With;


@With
@Value
@Builder
@Document
@RequiredArgsConstructor
public class Order {

	@Id
	private final String id;
	private final String customerId;
	private final OrderStatus status;
	private final List<OrderItem> orderItems;
	private final Address shippingAddress;
	private final Address billingAddress;
	private final PaymentDetails paymentDetails;
	private final Instant createdAt;
	private final Instant updatedAt;
}
