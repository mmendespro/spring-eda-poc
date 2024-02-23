package net.local.poc.orders.service.handlers.model;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class ApiErrorResponse {
	
	private final String message;
}
