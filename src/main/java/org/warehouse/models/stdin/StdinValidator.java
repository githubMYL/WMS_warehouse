package org.warehouse.models.stdin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;


@Component
@RequiredArgsConstructor
public class StdinValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return StdinForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		StdinForm stdinForm = (StdinForm)target;

		/**
		 * 1. 입고일자가 현재 날짜보다 앞선 경우
		 */

		if(LocalDate.now().isAfter(stdinForm.getStdinDt())) {
			errors.rejectValue("stdinDt", "Validation.invalid.stdinDt");
		}

	}
}
