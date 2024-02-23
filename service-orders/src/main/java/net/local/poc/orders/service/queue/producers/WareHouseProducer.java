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
public class WareHouseProducer {
    
    private final RabbitTemplate rabbitTemplate;

    private static final String WAREHOUSE_STOCK_RESERVE_TOPIC = "warehouse.stock.reserve";
    private static final String WAREHOUSE_STOCK_RELEASE_TOPIC = "warehouse.stock.release";
    private static final String WAREHOUSE_SHIPMENT_DISPATCH_TOPIC = "warehouse.shipment.dispatch";

    public void sendStockReservationEvent(Order order) {
        log.info("reserving stock for order {} by customer {}", order.getId(), order.getCustomerId());
        var key = String.format("%s-stock-reservation", order.getId());
        rabbitTemplate.convertAndSend(WAREHOUSE_STOCK_RESERVE_TOPIC, order, new CorrelationData(key));
    }

    public void sendStockReleaseEvent(Order order) {
        log.info("releasing stock for order {}", order.getId());
        var key = String.format("%s-stock-release", order.getId());
        rabbitTemplate.convertAndSend(WAREHOUSE_STOCK_RELEASE_TOPIC, order, new CorrelationData(key));
    }

    public void sendOrderDispatchEvent(Order order) {
        log.info("preparing order {} for dispatch", order.getId());
        var key = String.format("%s-shipment-dispatch", order.getId());
        rabbitTemplate.convertAndSend(WAREHOUSE_SHIPMENT_DISPATCH_TOPIC, order, new CorrelationData(key));
        log.info("------------------------------------------------------------------------------ <<");
    }
}
