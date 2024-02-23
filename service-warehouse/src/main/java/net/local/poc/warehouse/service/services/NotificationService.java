package net.local.poc.warehouse.service.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.local.poc.warehouse.service.model.Shipment;
import net.local.poc.warehouse.service.queue.producers.NotificationProducer;

@Service
@RequiredArgsConstructor
public class NotificationService {

 	private final NotificationProducer notificationProducer;

	public void informCustomerAboutCancellation(String customerId, String orderId, String message) {
		notificationProducer.sendOrderCancellationEvent(customerId, orderId, message);
	}

	public void informCustomerAboutShipment(Shipment shipment) {
		notificationProducer.sendShipmentPreparationEvent(shipment);
	}
}
