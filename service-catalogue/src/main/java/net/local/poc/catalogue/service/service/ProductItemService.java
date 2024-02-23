package net.local.poc.catalogue.service.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.local.poc.catalogue.service.handlers.exceptions.ItemNotFound;
import net.local.poc.catalogue.service.model.ProductItem;
import net.local.poc.catalogue.service.repository.ProductItemRepository;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductItemService {

	private final ProductItemRepository productItemRepository;

	public Mono<ProductItem> get(String id) {
		return productItemRepository.findById(id).switchIfEmpty(Mono.error(new ItemNotFound(id)));
	}

	public Mono<ProductItem> create(ProductItem item) {
		return productItemRepository.save(item);
	}
}
