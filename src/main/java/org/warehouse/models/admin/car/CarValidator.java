package org.warehouse.models.admin.car;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class CarValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return CarForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CarForm carForm = (CarForm) target;


	}
}
