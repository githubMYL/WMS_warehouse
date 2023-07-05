package org.warehouse.models.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.warehouse.configs.models.mapper.CustDAO;
import org.warehouse.configs.models.mapper.UserDAO;
import org.warehouse.controllers.admins.JoinForm;
import org.warehouse.controllers.users.UserInfo;

@Service
@RequiredArgsConstructor
public class UserJoinService {
	private final UserDAO userDAO;
	private final CustDAO custDAO;
	private final PasswordEncoder passwordEncoder;
	private final HttpSession session;

	public void join(JoinForm joinForm) {
		UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

		UserVO userVO = new ModelMapper().map(joinForm, UserVO.class);

		userVO.setUserPw(passwordEncoder.encode(joinForm.getUserPw()));
		userVO.setCustCd(custDAO.getCustByCustNm(joinForm.getCustCd()).getCustCd());
		userVO.setRegNm(userInfo.getUserNm());

		userDAO.insertUser(userVO);
	}

}
