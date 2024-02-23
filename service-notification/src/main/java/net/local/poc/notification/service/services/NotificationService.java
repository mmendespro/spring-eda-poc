package net.local.poc.notification.service.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.local.poc.notification.service.clients.CustomerServiceClient;
import net.local.poc.notification.service.model.Invoice;
import net.local.poc.notification.service.model.Shipment;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class NotificationService {

	private final CustomerServiceClient customerServiceClient;

	public Mono<Void> notifyAboutCancellation(String customerId, String orderId, String message) {
		return Mono.empty();
	}

	public Mono<Void> notifyAboutShipping(Shipment shipment) {
		return Mono.empty();
	}

	public Mono<Void> notifyAboutPayment(Invoice invoice) {
		return Mono.empty();
	}
}
