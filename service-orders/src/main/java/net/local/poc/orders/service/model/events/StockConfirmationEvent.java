package net.local.poc.orders.service.model.events;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StockConfirmationEvent(@JsonProperty("orderId") String orderId) implements Serializable {
}
