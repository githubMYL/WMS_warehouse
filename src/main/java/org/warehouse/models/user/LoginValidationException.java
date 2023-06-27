package org.warehouse.models.user;

import org.springframework.http.HttpStatus;
import org.warehouse.commons.CommonException;

public class LoginValidationException extends CommonException {
	private String field;

	public LoginValidationException(String code) {
		super(bundleValidation.getString(code), HttpStatus.BAD_REQUEST);
	}

	public LoginValidationException(String field, String code) {
		this(code);
		this.field = field;
	}

	public String getField() {
		return field;
	}
}
