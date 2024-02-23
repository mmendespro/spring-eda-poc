package net.local.poc.notification.service.queue.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.local.poc.notification.service.model.Invoice;
import net.local.poc.notification.service.model.Shipment;
import net.local.poc.notification.service.model.events.OrderCancellationEvent;
import net.local.poc.notification.service.services.NotificationService;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventsConsumer {
	
	private final NotificationService notificationService;
	private static final String NOTIFICATION_ORDER_CANCELLED_TOPIC = "notification.order.cancelled";
	private static final String NOTIFICATION_ORDER_PAYED_TOPIC = "notification.order.paid";
	private static final String NOTIFICATION_ORDER_SHIPPING_TOPIC = "notification.order.shipping";

	@RabbitListener(queues = NOTIFICATION_ORDER_CANCELLED_TOPIC)
	public void notifyAboutCancellation(OrderCancellationEvent event) {
		log.info("received order cancelled notification for customer {}", event.getCustomerId());
		notificationService.notifyAboutCancellation(event.getCustomerId(), event.getOrderId(), event.getMessage())
			.subscribe();
	}

	@RabbitListener(queues = NOTIFICATION_ORDER_SHIPPING_TOPIC)
	public void notifyAboutShipping(Shipment shipment) {
		log.info("received shipment notification for customer {}", shipment.getCustomerId());
		notificationService.notifyAboutShipping(shipment).subscribe();
	}

	@RabbitListener(queues = NOTIFICATION_ORDER_PAYED_TOPIC)
	public void notifyAboutPayment(Invoice invoice) {
		log.info("received invoice notification for customer {}", invoice.getCustomerId());
		notificationService.notifyAboutPayment(invoice).subscribe();
	}
}
