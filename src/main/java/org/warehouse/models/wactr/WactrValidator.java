package org.warehouse.models.wactr;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.warehouse.commons.validator.NumberValidator;
import org.warehouse.configs.models.mapper.WactrDAO;

@Component
@RequiredArgsConstructor
public class WactrValidator implements Validator, NumberValidator {
	private final WactrDAO wactrDAO;

	@Override
	public boolean supports(Class<?> clazz) {
		return WactrForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		WactrForm wactrForm = (WactrForm) target;
		String wactrCd = wactrForm.getWactrCd();
		String wactrNm = wactrForm.getWactrNm();
		String tel = wactrForm.getTel();
		String reech = wactrForm.getReech();
		String jackee = wactrForm.getJackee();
		String diesel = wactrForm.getDiesel();
		String racNum = wactrForm.getRacNum();
		String numInPer = wactrForm.getNumInPer();

		/**
		 * 1. 물류센터 코드 중복 확인
		 * 2. 물류센터 명 중복 확인
		 * 3. 리치, 쟉키, 디젤, 랙 수, 투입인원 수 숫자 형식 확인
		 */


		if(wactrCd != null && !wactrCd.isBlank() && (wactrDAO.getWactrByCd(wactrCd) != null)) {
			errors.rejectValue("wactrCd", "Validation.duplicate.wactrCd");
		}

		if(wactrNm != null && !wactrNm.isBlank() && (wactrDAO.getWactrByNm(wactrNm) != null)) {
			errors.rejectValue("wactrNm", "Validation.duplicate.wactrNm");
		}

		if(!isInteger(reech) || !isInteger(jackee) || !isInteger(diesel) || !isInteger(racNum) || !isInteger(numInPer)) {
			errors.rejectValue("numInPer", "Validation.notInteger.numInPer");
		}


	}
}
