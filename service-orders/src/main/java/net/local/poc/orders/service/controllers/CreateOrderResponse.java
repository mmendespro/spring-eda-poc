package net.local.poc.orders.service.controllers;

import lombok.Value;
import org.springframework.web.bind.annotation.RequestMapping;

@Value
@RequestMapping
public class CreateOrderResponse {
	
	private final String id;
}
