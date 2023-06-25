package org.warehouse.commons.validator;

public interface NumberValidator {
	default boolean isInteger(String strValue) {
		try{
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
