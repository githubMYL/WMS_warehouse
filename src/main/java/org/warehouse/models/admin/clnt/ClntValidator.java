package org.warehouse.models.admin.clnt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ClntValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return ClntForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ClntForm clntForm = (ClntForm)target;

	}
}
