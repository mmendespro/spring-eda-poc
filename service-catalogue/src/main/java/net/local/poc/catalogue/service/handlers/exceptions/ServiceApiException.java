package net.local.poc.catalogue.service.handlers.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceApiException extends RuntimeException {

	private final HttpStatus httpStatus;

	public ServiceApiException(HttpStatus httpStatus, String message) {
		super(message);
		this.httpStatus = httpStatus;
	}
}
