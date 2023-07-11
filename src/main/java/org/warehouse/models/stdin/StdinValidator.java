package org.warehouse.models.stdin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.warehouse.configs.models.mapper.ClntDAO;
import org.warehouse.configs.models.mapper.ItemInfoDAO;
import org.warehouse.configs.models.mapper.LocDAO;
import org.warehouse.configs.models.mapper.WactrDAO;
import org.warehouse.models.admin.clnt.ClntVO;
import org.warehouse.models.baseinfo.iteminfo.ItemInfoVO;
import org.warehouse.models.baseinfo.loc.LocVO;
import org.warehouse.models.baseinfo.wactr.WactrVO;

import java.time.LocalDate;
import java.util.List;


@Component
@RequiredArgsConstructor
public class StdinValidator implements Validator {
	private final ClntDAO clntDAO;
	private final LocDAO locDAO;
	private final ItemInfoDAO itemInfoDAO;


	@Override
	public boolean supports(Class<?> clazz) {
		return StdinForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		StdinForm stdinForm = (StdinForm)target;

		/**
		 * 0. null값 체크
		 * 1. 입고일자가 현재 날짜보다 앞선 경우
		 * 2. 고객사, 상품, 로케이션 존재 검증
		 * 3. 입고 수량 검증 - 총 입고 수량 = 정상 수량 + 불량 수량
		 */

		// 0. null값 체크
		if(stdinForm.getStdinDt() == null) {
			errors.rejectValue("stdinDt", "Validation.null.stdinDt");
		}
		if(stdinForm.getClntCd() == null) {
			errors.rejectValue("clntCd", "Validation.null.clntCd");
		}
		if(stdinForm.getItemCd() == null) {
			errors.rejectValue("itemCd", "Validation.null.itemCd");
		}
		if(stdinForm.getLocCd() == null) {
			errors.rejectValue("locCd", "Validation.null.locCd");
		}

		/*
		// 1. 입고일자가 현재 날짜보다 앞선 경우
		LocalDate stdinDt = stdinForm.getStdinDt();
		if(stdinDt != null && stdinDt.isBefore(LocalDate.now())) {
			errors.rejectValue("stdinDt", "Validation.isBefore.stdinDt");
		}
		 */


		// 2. 고객사, 상품, 로케이션 존재 검증
		ClntVO clntChk = clntDAO.getClntByCd(stdinForm.getClntCd());
		List<LocVO> locChk = locDAO.getSearchList(stdinForm.getLocCd());
		ItemInfoVO itemChk = itemInfoDAO.getItem(stdinForm.getItemCd());
		if(clntChk == null) {
			errors.rejectValue("clntCd", "Validation.notExist.clntCd");
		} else if (locChk == null) {
			errors.rejectValue("locCd", "Validation.notExist.locCd");
		} else if (itemChk == null) {
			errors.rejectValue("itemCd", "Validation.notExist.itemCd");
		}


		// 3. 입고 수량 검증
		if(stdinForm.getBeforeStdin() != null && !(stdinForm.getBeforeStdin() == (stdinForm.getFault() + stdinForm.getNormal()))){
			errors.rejectValue("beforeStdin", "Validation.incorrect.beforeStdin");
		}
	}
}
