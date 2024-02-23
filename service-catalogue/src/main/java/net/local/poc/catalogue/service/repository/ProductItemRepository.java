package net.local.poc.catalogue.service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import net.local.poc.catalogue.service.model.ProductItem;

public interface ProductItemRepository extends ReactiveMongoRepository<ProductItem, String> {
}
