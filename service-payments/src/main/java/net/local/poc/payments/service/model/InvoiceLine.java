package net.local.poc.payments.service.model;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class InvoiceLine {
	
	private final String itemId;
	private final String fullItemDescription;
	private final Integer quantity;
	private final BigDecimal price;

	public InvoiceLine(ProductItem item, int quantity) {
		this.itemId = item.getId();
		this.quantity = quantity;
		this.price = item.getPrice();
		this.fullItemDescription = String.format("%s - %s", item.getName(), item.getDescription());
	}
}
