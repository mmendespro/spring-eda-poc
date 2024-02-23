package net.local.poc.warehouse.service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
@Document
@RequiredArgsConstructor
public class StockLine {

	@Id
	private final String itemId;
	private final Integer amountAvailable;
	private final Integer amountReserved;

	public StockLine reserve(int quantity) {
		return new StockLine(itemId, amountAvailable-quantity, amountReserved+quantity);
	}

	public StockLine clearReservation(int quantity) {
		return new StockLine(itemId, amountAvailable+quantity, amountReserved-quantity);
	}
}
