package net.local.poc.payments.service.model.events;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class OrderCancellationEvent {
	private final String orderId;
	private final String customerId;
	private final String message;
}
