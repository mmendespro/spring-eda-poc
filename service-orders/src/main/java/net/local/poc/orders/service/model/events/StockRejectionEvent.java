package net.local.poc.orders.service.model.events;

import java.io.Serializable;

public record StockRejectionEvent(String orderId, String message) implements Serializable {
}
