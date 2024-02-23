package net.local.poc.warehouse.service.queue.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.local.poc.warehouse.service.model.Order;
import net.local.poc.warehouse.service.services.NotificationService;
import net.local.poc.warehouse.service.services.ShipmentService;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShipmentEventConsumer {

    private final ShipmentService shipmentService;
    private final NotificationService notificationService;
    private static final String WAREHOUSE_SHIPMENT_DISPATCH_TOPIC = "warehouse.shipment.dispatch";

    @RabbitListener(queues = WAREHOUSE_SHIPMENT_DISPATCH_TOPIC)
    public void dispatch(final Order order) {
        log.info("received shipment dispatch event for order {}", order.getId());
        shipmentService.prepare(order)
            .doOnNext(notificationService::informCustomerAboutShipment)
            .subscribe();
    }
}
