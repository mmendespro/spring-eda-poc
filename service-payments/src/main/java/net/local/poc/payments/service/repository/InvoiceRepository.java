package net.local.poc.payments.service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import net.local.poc.payments.service.model.Invoice;

public interface InvoiceRepository extends ReactiveMongoRepository<Invoice, String> {
}
