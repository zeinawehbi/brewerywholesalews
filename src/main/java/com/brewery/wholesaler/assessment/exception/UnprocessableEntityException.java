package com.brewery.wholesaler.assessment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends Exception {
	private static final long serialVersionUID = 1L;

	public UnprocessableEntityException(String message) {
		super(message);
	}
}