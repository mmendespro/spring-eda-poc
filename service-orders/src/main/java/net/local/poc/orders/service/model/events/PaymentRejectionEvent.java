package net.local.poc.orders.service.model.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRejectionEvent {

	private String orderId;
	private String message;
}
