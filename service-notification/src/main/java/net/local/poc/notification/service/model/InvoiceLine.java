package net.local.poc.notification.service.model;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;

@Value
@RequiredArgsConstructor
public class InvoiceLine {
	private final String itemId;
	private final String fullItemDescription;
	private final Integer quantity;
	private final BigDecimal price;
}
