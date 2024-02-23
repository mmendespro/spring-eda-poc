package net.local.poc.orders.service.queue.producers;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.local.poc.orders.service.model.Order;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentProducer {
    
    private final RabbitTemplate rabbitTemplate;
    private static final String FINANCE_PAYMENT_PROCESS_TOPIC = "finance.payment.process";

    public void sendPaymentProcessingEvent(Order order) {
        log.info("processing payment for order {} by customer {}", order.getId(), order.getCustomerId());
        var key = String.format("%s-payment-processing", order.getId());
        rabbitTemplate.convertAndSend(FINANCE_PAYMENT_PROCESS_TOPIC, order, new CorrelationData(key));
    }
}
