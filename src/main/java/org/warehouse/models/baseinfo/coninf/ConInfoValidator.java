package org.warehouse.models.baseinfo.coninf;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ConInfoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ConInfoVO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		/**
		 * 1. 월 미니멈 0 or isBlank 체크
		 * 2. 파렛트보관료 0 or isBlank 체크
		 * 3. 거래시작일이 거래만료일보다 작은지 체크
		 * 4. 거래만료일이 거래시작일보다 큰지 체크
		 */

		ConInfoVO conInfoVO = (ConInfoVO) target;
		Long mMin = conInfoVO.getMMin();
		Long pltFee = conInfoVO.getPltFee();
		LocalDate transSdt = conInfoVO.getTransSdt();
		LocalDate transEdt = conInfoVO.getTransEdt();

		// 1. 월 미니멈 0 or isBlank 체크
		if (mMin == null) {
			errors.rejectValue("mMin", "Validation.incorrect.mMin");
		} else if (mMin < 0) {
			errors.rejectValue("mMin", "Validation.incorrect.mMin");
		}
		// 2. 파렛트보관료 0 or isBlank 체크

		if (pltFee == null) {
			errors.rejectValue("pltFee", "Validation.incorrect.pltFee");
		} else if (pltFee < 0) {
			errors.rejectValue("pltFee", "Validation.incorrect.pltFee");
		}
		// 3. 거래시작일이 거래만료일보다 작은지 체크
		// 4. 거래만료일이 거래시작일보다 큰지 체크
		if ((transSdt != null && transEdt != null) && transSdt.isAfter(transEdt)) {
			//System.out.println("error!!!!!!!!!!!!!!!");
			errors.rejectValue("transSdt", "Validation.incorrect.transSdt");

		}
	}
}