package net.local.poc.payments.service.queue.producers;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.local.poc.payments.service.model.Invoice;
import net.local.poc.payments.service.model.events.OrderCancellationEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventProducer {

	private final RabbitTemplate rabbitTemplate;
	private static final String NOTIFICATION_ORDER_CANCELLED_TOPIC = "notification.order.cancelled";
	private static final String NOTIFICATION_ORDER_PAYED_TOPIC = "notification.order.paid";

	public void sendOrderCancellationEvent(String customerId, String orderId, String message) {
		log.info("informing customer {} about cancellation of his order {}", customerId, orderId);
		var event = new OrderCancellationEvent(orderId, customerId, message);
		var key = String.format("%s-notify-cancelled", orderId);
		rabbitTemplate.convertAndSend(NOTIFICATION_ORDER_CANCELLED_TOPIC, event, new CorrelationData(key));
	}

	public void sendPaymentSuccessEvent(Invoice invoice) {
		log.info("informing customer {} about successfull payment for order {}", invoice.getCustomerId(), invoice.getOrderId());
		var key = String.format("%s-notify-invoice", invoice.getOrderId());
		rabbitTemplate.convertAndSend(NOTIFICATION_ORDER_PAYED_TOPIC, invoice, new CorrelationData(key));
	}
}
