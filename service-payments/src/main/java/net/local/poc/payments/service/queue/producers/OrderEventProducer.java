package net.local.poc.payments.service.queue.producers;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.local.poc.payments.service.model.events.PaymentConfirmationEvent;
import net.local.poc.payments.service.model.events.PaymentRejectionEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventProducer {

	private final RabbitTemplate rabbitTemplate;
	private static final String ORDER_PAYMENT_CONFIRM_TOPIC = "order.payment.confirm";
	private static final String ORDER_PAYMENT_REJECT_TOPIC = "order.payment.reject";

	public void sendPaymentProcessingFailure(String orderId, String message) {
		log.info("rejecting payment for order {}: {}", orderId, message);
		var event = new PaymentRejectionEvent(orderId, message);
		var key = String.format("%s-payment-rejection", orderId);
		rabbitTemplate.convertAndSend(ORDER_PAYMENT_REJECT_TOPIC, event, new CorrelationData(key));
	}

	public void sendPaymentProcessingSuccess(String orderId) {
		log.info("confirming payment for order {}", orderId);
		var event = new PaymentConfirmationEvent(orderId);
		var key = String.format("%s-payment-confirmation", orderId);
		rabbitTemplate.convertAndSend(ORDER_PAYMENT_CONFIRM_TOPIC, event, new CorrelationData(key));
	}
}
