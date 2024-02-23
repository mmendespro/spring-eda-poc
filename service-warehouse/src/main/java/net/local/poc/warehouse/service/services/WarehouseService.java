package net.local.poc.warehouse.service.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.local.poc.warehouse.service.exceptions.ItemNotFound;
import net.local.poc.warehouse.service.exceptions.ItemNotInStock;
import net.local.poc.warehouse.service.model.OrderItem;
import net.local.poc.warehouse.service.model.StockLine;
import net.local.poc.warehouse.service.queue.producers.StockProducer;
import net.local.poc.warehouse.service.repository.StockLineRepository;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WarehouseService {

	private final StockLineRepository stockLineRepository;
	private final StockProducer stockProducer;

	public void initdb() {
		stockLineRepository.save(new StockLine("item-1", 10, 4));
		stockLineRepository.save(new StockLine("item-2", 5, 2));
		stockLineRepository.save(new StockLine("item-3", 3, 5));
		stockLineRepository.save(new StockLine("item-4", 2, 2));
		stockLineRepository.save(new StockLine("item-5", 0, 0));
	}

	public Mono<Void> verifyIsInStock(OrderItem orderItem) {
		var itemId = orderItem.getItemId();
		var quantity = orderItem.getQuantity();
		return stockLineRepository.findById(itemId)
								  .switchIfEmpty(Mono.error(new ItemNotFound(itemId)))
								  .flatMap(sl -> sl.getAmountAvailable() >= quantity ? Mono.empty() : Mono.error(new ItemNotInStock(itemId, sl.getAmountAvailable(), quantity )));
	}

	public Mono<StockLine> reserveStock(OrderItem orderItem) {
		var itemId = orderItem.getItemId();
		var quantity = orderItem.getQuantity();
		return stockLineRepository.findById(itemId)
								  .map(sl -> sl.reserve(quantity))
								  .flatMap(stockLineRepository::save);
	}

	public Mono<StockLine> clearStockReservation(OrderItem orderItem) {
		var itemId = orderItem.getItemId();
		var quantity = orderItem.getQuantity();
		return stockLineRepository.findById(itemId)
								  .map(sl -> sl.clearReservation(quantity))
								  .flatMap(stockLineRepository::save);
	}

	public void rejectStockReservation(String orderId, String message) {
		stockProducer.sendStockReservationFailure(orderId, message);
	}

	public void confirmStockReservation(String orderId) {
		stockProducer.sendStockReservationSuccess(orderId);
	}
}