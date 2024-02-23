package net.local.poc.orders.service.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.local.poc.orders.service.model.Order;
import net.local.poc.orders.service.model.OrderStatus;
import net.local.poc.orders.service.queue.producers.PaymentProducer;
import net.local.poc.orders.service.queue.producers.WareHouseProducer;
import net.local.poc.orders.service.repository.OrderRepository;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final PaymentProducer paymentProducer;
	private final WareHouseProducer wareHouseProducer;

	public Mono<Order> create(Order order) {
		return orderRepository.save(order);
	}

	public Mono<Order> updateStatus(String orderId, OrderStatus status) {
		//var order = orderRepository.findById(orderId).block();
		//var updatedOrder = order.withStatus(status).withUpdatedAt(Instant.now());
		//return orderRepository.save(updatedOrder);
		return orderRepository.findById(orderId)
		                      .map(order -> order.withStatus(status).withUpdatedAt(Instant.now()))
							  .doOnNext(orderRepository::save);
	}

	public void reserveStock(Order order) {
		wareHouseProducer.sendStockReservationEvent(order);
	}

	public void releaseStock(Order order) {
		wareHouseProducer.sendStockReleaseEvent(order);
	}

	public void dispatch(Order order) {
		wareHouseProducer.sendOrderDispatchEvent(order);
	}

	public void processPayment(Order order) {
		paymentProducer.sendPaymentProcessingEvent(order);
	}
}
