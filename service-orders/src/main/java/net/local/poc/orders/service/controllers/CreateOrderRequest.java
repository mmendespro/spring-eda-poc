package net.local.poc.orders.service.controllers;

import java.time.Instant;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import net.local.poc.orders.service.model.Address;
import net.local.poc.orders.service.model.Order;
import net.local.poc.orders.service.model.OrderItem;
import net.local.poc.orders.service.model.OrderStatus;
import net.local.poc.orders.service.model.PaymentDetails;

@Value
@Builder
@RequiredArgsConstructor
public class CreateOrderRequest {

	@NotEmpty
	private final String customerId;

	@NotEmpty
	@Valid
	private final List<OrderItem> orderItems;

	@NotNull
	@Valid
	private final Address shippingAddress;

	@NotNull
	@Valid
	private final Address billingAddress;

	@NotNull
	@Valid
	private final PaymentDetails paymentDetails;

	public Order toOrder() {
		return Order.builder()
					.billingAddress(billingAddress)
					.shippingAddress(shippingAddress)
					.orderItems(orderItems)
					.customerId(customerId)
					.paymentDetails(paymentDetails)
					.createdAt(Instant.now())
					.updatedAt(Instant.now())
					.status(OrderStatus.INITIATED_RESERVING_STOCK)
					.build();
	}
}
