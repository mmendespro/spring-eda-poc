package net.local.poc.orders.service.queue.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.local.poc.orders.service.model.OrderStatus;
import net.local.poc.orders.service.model.events.StockConfirmationEvent;
import net.local.poc.orders.service.model.events.StockRejectionEvent;
import net.local.poc.orders.service.service.OrderService;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockConsumer {

    private final OrderService orderService;
    private static final String ORDER_STOCK_CONFIRM_TOPIC = "order.stock.confirm";
    private static final String ORDER_STOCK_REJECT_TOPIC = "order.stock.reject";
    
    @RabbitListener(queues = {ORDER_STOCK_CONFIRM_TOPIC})
    public void confirmStock(final StockConfirmationEvent stockEvent) {
        log.info("received stock confirmation event for order {}", stockEvent.orderId());
        orderService.updateStatus(stockEvent.orderId(), OrderStatus.RESERVED_PROCESSING_PAYMENT)
                    .doOnNext(orderService::processPayment)
                    .subscribe();
    }

    @RabbitListener(queues = {ORDER_STOCK_REJECT_TOPIC})
    public void rejectStock(final StockRejectionEvent event) {
        log.info("received stock rejection event for order {}: {}", event.orderId(), event.message());
        orderService.updateStatus(event.orderId(), OrderStatus.CANCELLED_OUT_OF_STOCK).subscribe();
    }
}
