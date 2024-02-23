package net.local.poc.notification.service.model;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
public class Customer {
	private final String id;
	private final String firstName;
	private final String lastName;
	private final String email;
}
