package net.local.poc.orders.service.controllers;

import static org.springframework.http.HttpStatus.CREATED;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.local.poc.orders.service.model.Order;
import net.local.poc.orders.service.service.OrderService;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/orders")
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	@ResponseStatus(CREATED)
	public Mono<CreateOrderResponse> initiate(@Validated @RequestBody CreateOrderRequest request) {
		log.info(">> ------------------------------------------------------------------------------");
		log.info("initiate order request from customer {}", request.getCustomerId());
		return orderService.create(request.toOrder())
						   .doOnNext(orderService::reserveStock)
						   .map(Order::getId)
						   .map(CreateOrderResponse::new);
	}
}
