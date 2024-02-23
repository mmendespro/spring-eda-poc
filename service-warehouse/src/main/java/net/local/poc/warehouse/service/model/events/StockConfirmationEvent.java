package net.local.poc.warehouse.service.model.events;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class StockConfirmationEvent {
	private final String orderId;
}
