package org.warehouse.models.baseinfo.coninfo;

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
		 * 1. 고객사 검증
		 * 2. 월 미니멈 0 or isBlank 체크
		 * 3. 파렛트보관료 0 or isBlank 체크
		 * 4. 거래시작일과 거래종료일 체크
		 */

		ConInfoVO conInfoVO = (ConInfoVO) target;
		System.out.println("conInfoVO  ::  " + conInfoVO.toString());
//		System.out.println(">>>>>>>>>>>" + Long.class.isInstance(conInfoVO.getmMin()) );

		String clntCd = conInfoVO.getClntCd();
		Long mMin = conInfoVO.getMMin();
//		System.out.println("mMin :: " + mMin);
		Long pltFee = conInfoVO.getPltFee();
//		LocalDate transSdt = conInfoVO.getTransSdt();
//		LocalDate transEdt = conInfoVO.getTransEdt();

		// 1. 고객사 검증
		if (clntCd == null || clntCd.isBlank()) {
			errors.rejectValue("clntCd", "Validation.incorrect.clntCd");
		}

		// 2. 월 미니멈 0 or isBlank 체크
		if (mMin == null) {
			errors.rejectValue("MMin", "Validation.incorrect.mMin");
		} else if (mMin < 0 || mMin > 10000) {
			errors.rejectValue("MMin", "Validation.incorrect.mMin2");
		}
		// 3. 파렛트보관료 0 or isBlank 체크

		if (pltFee == null) {
			errors.rejectValue("pltFee", "Validation.incorrect.pltFee");
		} else if (pltFee < 0) {
			errors.rejectValue("pltFee", "Validation.incorrect.pltFee");
		}
		// 4. 거래시작일이 거래만료일보다 작은지 체크
//		if (transSdt != null && transEdt != null && !transSdt.isBefore(transEdt.plusDays(1))) {
//			errors.rejectValue("transSdt", "Validation.incorrect.transSdt");
//		}
	}
}