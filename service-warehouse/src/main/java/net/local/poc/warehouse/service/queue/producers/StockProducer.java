package net.local.poc.warehouse.service.queue.producers;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.local.poc.warehouse.service.model.events.StockConfirmationEvent;
import net.local.poc.warehouse.service.model.events.StockRejectionEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockProducer {

    private final RabbitTemplate rabbitTemplate;

    private static final String ORDER_STOCK_CONFIRM_TOPIC = "order.stock.confirm";
    private static final String ORDER_STOCK_REJECT_TOPIC = "order.stock.reject";
    
    public void sendStockReservationFailure(String orderId, String message) {
        log.info("rejecting stock reservation for order {}: {}", orderId, message);
        var event = new StockRejectionEvent(orderId, message);
        var key = String.format("%s-stock-rejection", orderId);
        rabbitTemplate.convertAndSend(ORDER_STOCK_REJECT_TOPIC, event, new CorrelationData(key));
    }

    public void sendStockReservationSuccess(String orderId) {
        log.info("confirming stock reservation for order {}", orderId);
        var event = new StockConfirmationEvent(orderId);
        var key = String.format("%s-stock-confirmation", orderId);
        rabbitTemplate.convertAndSend(ORDER_STOCK_CONFIRM_TOPIC, event, new CorrelationData(key));
    }
}
