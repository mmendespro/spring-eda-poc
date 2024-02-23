package net.local.poc.payments.service.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
@Document
@RequiredArgsConstructor
public class Invoice {
	@Id
	private final String id;
	private final String orderId;
	private final String customerId;
	private final List<InvoiceLine> invoiceLines;
	private final PaymentDetails paymentDetails;
	private final Address billingAddress;
	private final BigDecimal totalChargeAmount;
	private final Instant dateCreated;
}
