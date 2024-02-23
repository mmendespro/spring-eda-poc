package net.local.poc.orders.service.model;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
public class Address {
    
    private final String streetAddress;
    private final String city;
    private final String state;
    private final String zipCode;
}
