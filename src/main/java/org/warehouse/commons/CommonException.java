package org.warehouse.commons;

import org.springframework.http.HttpStatus;

import java.util.ResourceBundle;

public class CommonException extends RuntimeException {
	protected static ResourceBundle bundleValidation;
	protected static ResourceBundle bundleError;

	protected HttpStatus status;

	static {
		bundleValidation = ResourceBundle.getBundle("messages.validations");
		bundleError = ResourceBundle.getBundle("messages.errors");
	}

	public CommonException(String message) {
		this(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public CommonException(String message, HttpStatus status) {
		super(message);

		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}
}
