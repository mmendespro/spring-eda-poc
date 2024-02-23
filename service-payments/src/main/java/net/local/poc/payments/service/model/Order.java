package net.local.poc.payments.service.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
public class Order {

	private final String id;
	private final String customerId;
	private final List<OrderItem> orderItems;
	private final Address shippingAddress;
	private final Address billingAddress;
	private final PaymentDetails paymentDetails;
	private final Instant dateCreated;
	private final Instant dateUpdated;

	public Invoice toInvoice(List<InvoiceLine> invoiceLines) {
		var totalChargeAmount = invoiceLines.stream()
			.map(tl -> tl.getPrice().multiply(BigDecimal.valueOf(tl.getQuantity())))
			.reduce(BigDecimal.ZERO, BigDecimal::add);
		return Invoice.builder()
			.orderId(id)
			.customerId(customerId)
			.paymentDetails(paymentDetails)
			.billingAddress(billingAddress)
			.invoiceLines(invoiceLines)
			.dateCreated(Instant.now())
			.totalChargeAmount(totalChargeAmount)
			.build();
	}
}
