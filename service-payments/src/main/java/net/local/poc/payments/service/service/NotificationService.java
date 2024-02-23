package net.local.poc.payments.service.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.local.poc.payments.service.model.Invoice;
import net.local.poc.payments.service.queue.producers.NotificationEventProducer;

@Service
@RequiredArgsConstructor
public class NotificationService {

	private final NotificationEventProducer notificationEventProducer;

	public void informCustomerAboutCancellation(String customerId, String orderId, String message) {
		notificationEventProducer.sendOrderCancellationEvent(customerId, orderId, message);
	}

	public void informCustomerAboutPayment(Invoice invoice) {
		notificationEventProducer.sendPaymentSuccessEvent(invoice);
	}
}
