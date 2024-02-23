package net.local.poc.catalogue.service.handlers.model;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class ApiErrorResponse {
    private final String message;
}
