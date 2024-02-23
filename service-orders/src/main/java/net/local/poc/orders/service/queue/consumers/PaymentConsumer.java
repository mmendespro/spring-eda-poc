package net.local.poc.orders.service.queue.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.local.poc.orders.service.model.OrderStatus;
import net.local.poc.orders.service.model.events.PaymentConfirmationEvent;
import net.local.poc.orders.service.model.events.PaymentRejectionEvent;
import net.local.poc.orders.service.service.OrderService;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentConsumer {

    private final OrderService orderService;
    private static final String ORDER_PAYMENT_CONFIRM_TOPIC = "order.payment.confirm";
    private static final String ORDER_PAYMENT_REJECT_TOPIC = "order.payment.reject";

    @RabbitListener(queues = {ORDER_PAYMENT_CONFIRM_TOPIC})
    public void confirmPayment(final PaymentConfirmationEvent event) {
        log.info("received payment confirmation event for order {}", event.getOrderId());
        orderService.updateStatus(event.getOrderId(), OrderStatus.PAYED_PREPARING_FOR_SHIPMENT)
                    .doOnNext(orderService::dispatch)
                    .subscribe();
    }

    @RabbitListener(queues = {ORDER_PAYMENT_REJECT_TOPIC})
    public void rejectPayment(final PaymentRejectionEvent event) {
        log.info("received payment rejection event for order {}: {}", event.getOrderId(), event.getMessage());
        orderService.updateStatus(event.getOrderId(), OrderStatus.CANCELLED_PAYMENT_REJECTED)
                    .doOnNext(orderService::releaseStock)
                    .subscribe();
    }

}
