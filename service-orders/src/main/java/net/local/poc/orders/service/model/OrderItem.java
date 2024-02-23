package net.local.poc.orders.service.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class OrderItem {

	@NotEmpty
	private final String itemId;

	@Min(1)
	private final Integer quantity;
}
