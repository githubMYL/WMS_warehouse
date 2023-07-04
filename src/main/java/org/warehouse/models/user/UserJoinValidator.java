package org.warehouse.models.user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.warehouse.configs.models.mapper.UserDAO;
import org.warehouse.controllers.users.JoinForm;

@Component
@RequiredArgsConstructor
public class UserJoinValidator implements Validator {
	private final UserDAO userMapper;

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
		 *
		 */

		JoinForm joinForm = (JoinForm) target;
		String userId = joinForm.getUserId();
		String userPw = joinForm.getUserPw();
		String userPwRe = joinForm.getUserPwRe();

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
		if(userId != null && !userId.isBlank() && (userMapper.getUserById(userId) != null)){
			errors.rejectValue("userId", "Validation.duplicate.userId");
		}



		//2. 비밀번호 복잡성 체크 - 우선은 빼고 진행

		//3. 비밀번호와 비밀번호 확인란 일치
		if(userPw != null && !userPw.isBlank() && userPwRe != null && !userPwRe.isBlank() && !userPw.equals(userPwRe)){
			errors.rejectValue("userPwRe", "Validation.incorrect.userPwRe");
		}



	}
}
