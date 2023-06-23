package org.warehouse.models.user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.warehouse.configs.models.mapper.CustCtrDAO;
import org.warehouse.configs.models.mapper.CustDAO;
import org.warehouse.configs.models.mapper.UserDAO;
import org.warehouse.controllers.admins.JoinForm;
import org.warehouse.models.custctr.CustCtrVO;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserJoinValidator implements Validator {
	private final UserDAO userDAO;
	private final CustDAO custDAO;
	private final CustCtrDAO custCtrDAO;

	@Override
	public boolean supports(Class<?> clazz) {
		return JoinForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		/**
		 * 1. 아이디 중복 여부
		 * 2. 비밀번호 복잡성 체크( 안쓸듯 ? )
		 * 3. 비밀번호와 비밀번호 확인 일치
		 * 4. 휴대전화번호(선택) - 입력된 경우 형식 체크
		 * 5. 휴대전화번호가 입력된 경우 숫자만 추출해서 다시 커맨드 객체에 저장
		 * 6. 납품처 명이 존재하는지 검증
		 *
		 */

		JoinForm joinForm = (JoinForm) target;
		String userId = joinForm.getUserId();
		String userPw = joinForm.getUserPw();
		String userPwRe = joinForm.getUserPwRe();
		String custCd = joinForm.getCustCd();
		String custCtrCd = joinForm.getCustCtrCd();

		/** 기타 입력폼 정보들 - 추후에 검증 추가할지도?
		 String userNm = joinForm.getUserNm();
		 String userType = joinForm.getUserType();

		 String clntCd = joinForm.getClntCd();
		 String custCtrCd = joinForm.getCustCtrCd();
		 String custCd = joinForm.getCustCd();
		 String position = joinForm.getPosition();
		 String tel = joinForm.getTel();
		 String email = joinForm.getEmail();
		 */



		//1. 아이디 중복 여부
		if(userId != null && !userId.isBlank() && (userDAO.getUserById(userId) != null)){
			errors.rejectValue("userId", "Validation.duplicate.userId");
		}



		//2. 비밀번호 복잡성 체크 - 우선은 빼고 진행

		//3. 비밀번호와 비밀번호 확인란 일치
		if(userPw != null && !userPw.isBlank() && userPwRe != null && !userPwRe.isBlank() && !userPw.equals(userPwRe)){
			errors.rejectValue("userPwRe", "Validation.incorrect.userPwRe");
		}

		//6. 납품처 명이 존재하는지 검증
		if(custDAO.getCustByCustNm(custCd) == null) {
			errors.rejectValue("custCd", "Validation.notExist.cust");
		}
		joinForm.setCustCd(custDAO.getCustByCustNm(custCd).getCustCd());



		//7. 해당 납품처 코드를 활용, 납품센터 테이블에서 일치하는 납품센터 명이 존재하는지 검증
		CustCtrVO custCtr = custCtrDAO.getCustCtrByCustCdCustCtrNm(joinForm.getCustCd(), joinForm.getCustCtrCd());
		if(custCtr == null) {
			errors.rejectValue("custCtrCd", "Validation.notExist.custCtr");
		}
		joinForm.setCustCtrCd(custCtr.getCustCtrCd());

		System.out.println(joinForm);


	}
}