package net.local.poc.warehouse.service.queue.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.local.poc.warehouse.service.model.Order;
import net.local.poc.warehouse.service.services.NotificationService;
import net.local.poc.warehouse.service.services.WarehouseService;
import reactor.core.publisher.Flux;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockEventConsumer {
    
    private final WarehouseService warehouseService;
    private final NotificationService notificationService;
    private static final String WAREHOUSE_STOCK_RESERVE_TOPIC = "warehouse.stock.reserve";
    private static final String WAREHOUSE_STOCK_RELEASE_TOPIC = "warehouse.stock.release";

    @RabbitListener(queues = WAREHOUSE_STOCK_RESERVE_TOPIC)
    public void reserveStock(final Order order) {
        log.info("received stock reservation event for order {}", order.getId());
        Flux.fromIterable(order.getOrderItems())
            .flatMap(warehouseService::verifyIsInStock)
            .thenMany(Flux.fromIterable(order.getOrderItems()))
            .doOnNext(warehouseService::reserveStock)
            .onErrorStop()
            .doOnError(error -> warehouseService.rejectStockReservation(order.getId(), error.getMessage()))
            .doOnError(error -> notificationService.informCustomerAboutCancellation(order.getCustomerId(), order.getId(), error.getMessage()))
            .doOnComplete(() -> warehouseService.confirmStockReservation(order.getId()))
            .subscribe();
    }

    @RabbitListener(queues = WAREHOUSE_STOCK_RELEASE_TOPIC)
    public void releaseStock(final Order order) {
        log.info("received stock release event for order {}", order.getId());
        Flux.fromIterable(order.getOrderItems())
            .flatMap(warehouseService::clearStockReservation)
            .subscribe();
    }
}
