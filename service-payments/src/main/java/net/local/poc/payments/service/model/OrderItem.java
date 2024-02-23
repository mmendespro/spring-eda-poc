package net.local.poc.payments.service.model;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class OrderItem {
	private final String itemId;
	private final Integer quantity;
}
