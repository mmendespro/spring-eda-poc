package net.local.poc.payments.service.model.events;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class PaymentConfirmationEvent {
	private final String orderId;
}
