package net.local.poc.catalogue.service.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.local.poc.catalogue.service.model.ProductItem;
import net.local.poc.catalogue.service.service.ProductItemService;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("v1/products")
public class ProductItemController {

	private final ProductItemService productItemService;

	@GetMapping("/{id}")
	public Mono<ProductItem> getOne(@PathVariable String id) {
		return productItemService.get(id);
	}

	@PostMapping
	public Mono<ProductItem> createProductItem(@RequestBody ProductItem item) {
		return productItemService.create(item);
	}
	
}
