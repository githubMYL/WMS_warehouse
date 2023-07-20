package org.warehouse.models.baseinfo.loc;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.warehouse.commons.validator.NumberValidator;

@Component
@RequiredArgsConstructor
public class LocJoinValidator implements Validator, NumberValidator {
	@Override
	public boolean supports(Class<?> clazz) {

		return LocVO.class.isAssignableFrom(clazz);
	}


	// 숫자형식이면서 중간에 - 입력 체크
	private boolean isLocAddr(String locAddr) {
		return !locAddr.matches("^[0-9]*$") && locAddr.matches("^[0-9]+(-[0-9]+)*$");
	}

	@Override
	public void validate(Object target, Errors errors) {
		/**
		 * loc
		 * 빈값 체크
		 * 로케이션명 영어 , 숫자 1개이상 포함
		 * 로케이션 주소 숫자 , 중간데 "-" 포함
		 * 랙수 숫자값만 허용
		 * */


		LocVO locVO = (LocVO) target;
		String locAddr = locVO.getLoc_addr();


		if(!isLocAddr(locAddr)){
			errors.rejectValue("loc_addr", "Validation.notInteger.hyphen");
		}


	}


}