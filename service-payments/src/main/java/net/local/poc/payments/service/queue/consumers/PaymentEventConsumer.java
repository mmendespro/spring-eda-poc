package net.local.poc.payments.service.queue.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.local.poc.payments.service.model.Order;
import net.local.poc.payments.service.service.NotificationService;
import net.local.poc.payments.service.service.PaymentService;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventConsumer {
  
	private final PaymentService paymentService;
	private final NotificationService notificationService;
	private static final String FINANCE_PAYMENT_PROCESS_TOPIC = "finance.payment.process";

	@RabbitListener(queues = FINANCE_PAYMENT_PROCESS_TOPIC)
	public void processPayment(final Order order) {
		log.info("received payment processing event for order {}", order.getId());
		paymentService.processPayment(order)
					  .doOnError(error -> paymentService.rejectPayment(order.getId(), error.getMessage()))
					  .doOnError(error -> notificationService.informCustomerAboutCancellation(order.getCustomerId(), order.getId(), error.getMessage()))
					  .doOnSuccess(paymentService::confirmPayment)
					  .doOnSuccess(notificationService::informCustomerAboutPayment)
					  .subscribe();
	}
}
