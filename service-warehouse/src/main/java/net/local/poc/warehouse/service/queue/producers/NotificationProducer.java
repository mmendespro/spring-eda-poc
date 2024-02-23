package net.local.poc.warehouse.service.queue.producers;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.local.poc.warehouse.service.model.Shipment;
import net.local.poc.warehouse.service.model.events.OrderCancellationEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationProducer {

    private final RabbitTemplate rabbitTemplate;
    private static final String NOTIFICATION_ORDER_CANCELLED_TOPIC = "notification.order.cancelled";
    private static final String NOTIFICATION_ORDER_SHIPPING_TOPIC = "notification.order.shipping";
    
    public void sendOrderCancellationEvent(String customerId, String orderId, String message) {
        log.info("informing customer {} about cancellation of his order {}", customerId, orderId);
        var event = new OrderCancellationEvent(orderId, customerId, message);
        var key = String.format("%s-notify-cancelled", orderId);
        rabbitTemplate.convertAndSend(NOTIFICATION_ORDER_CANCELLED_TOPIC, event, new CorrelationData(key));
    }

    public void sendShipmentPreparationEvent(Shipment shipment) {
        log.info("informing customer {} about preparation of his order {} for shipment", shipment.getCustomerId(), shipment.getOrderId());
        var key = String.format("%s-notify-shipment", shipment.getOrderId());
        rabbitTemplate.convertAndSend(NOTIFICATION_ORDER_SHIPPING_TOPIC, shipment, new CorrelationData(key));
    }
}
