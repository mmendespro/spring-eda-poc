package net.local.poc.payments.service.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.local.poc.payments.service.clients.CatalogueServiceClient;
import net.local.poc.payments.service.handlers.exceptions.PaymentProcessingError;
import net.local.poc.payments.service.model.Invoice;
import net.local.poc.payments.service.model.InvoiceLine;
import net.local.poc.payments.service.model.Order;
import net.local.poc.payments.service.model.OrderItem;
import net.local.poc.payments.service.queue.producers.OrderEventProducer;
import net.local.poc.payments.service.repository.InvoiceRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PaymentService {
    
    private static final Random RNG = new Random();

    private final CatalogueServiceClient catalogueServiceClient;
    private final InvoiceRepository invoiceRepository;
    private final OrderEventProducer orderEventProducer;

    public Mono<Invoice> processPayment(Order order) {
        return processPayment(order, RNG);
    }

    public Mono<Invoice> processPayment(Order order, Random rng) {
        /* Complex payment processing logic */
        return Flux.fromIterable(order.getOrderItems())
            .flatMap(this::createInvoiceLine)
            .collectList()
            .map(order::toInvoice)
            .flatMap(invoice -> rng.nextBoolean() ? invoiceRepository.save(invoice) : Mono.error(new PaymentProcessingError(order.getId())));
    }

    private Mono<InvoiceLine> createInvoiceLine(OrderItem orderItem) {
        return catalogueServiceClient.findProductItem(orderItem.getItemId())
                                     .map(item -> new InvoiceLine(item, orderItem.getQuantity()));
    }

    public void rejectPayment(String orderId, String message) {
        orderEventProducer.sendPaymentProcessingFailure(orderId, message);
    }

    public void confirmPayment(Invoice invoice) {
        orderEventProducer.sendPaymentProcessingSuccess(invoice.getOrderId());
    }
}
