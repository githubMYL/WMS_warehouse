package org.warehouse.models.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.warehouse.configs.models.mapper.CustCtrDAO;
import org.warehouse.configs.models.mapper.CustDAO;
import org.warehouse.configs.models.mapper.UserDAO;
import org.warehouse.controllers.admins.JoinForm;
import org.warehouse.controllers.users.UserInfo;

@Service
@RequiredArgsConstructor
public class UserJoinService {
	private final UserDAO userDAO;
	private final CustDAO custDAO;
	private final CustCtrDAO custCtrDAO;
	private final PasswordEncoder passwordEncoder;
	private final HttpSession session;

	public void join(JoinForm joinForm) {
		UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

		UserVO userVO = new ModelMapper().map(joinForm, UserVO.class);

		if(joinForm.getFlag() == null) {
			userVO.setUserPw(passwordEncoder.encode(joinForm.getUserPw()));
			userVO.setCustCd(custDAO.getCustByCustNm(joinForm.getCustNm()) == null ? "" : custDAO.getCustByCustNm(joinForm.getCustNm()).getCustCd());
			userVO.setCustCtrCd(custCtrDAO.getCustCtrByNm(joinForm.getCustCtrNm()) == null ? "" : custCtrDAO.getCustCtrByNm(joinForm.getCustCtrNm()).getCustCtrCd());
			userVO.setRegNm(userInfo.getUserNm());

			userDAO.insertUser(userVO);
		} else {
			userVO.setUserPw(passwordEncoder.encode(joinForm.getUserPw()));
			userVO.setCustCd(custDAO.getCustByCustNm(joinForm.getCustNm()) == null ? "" : custDAO.getCustByCustNm(joinForm.getCustNm()).getCustCd());
			userVO.setCustCtrCd(custCtrDAO.getCustCtrByNm(joinForm.getCustCtrNm()) == null ? "" : custCtrDAO.getCustCtrByNm(joinForm.getCustCtrNm()).getCustCtrCd());
			userVO.setModNm(userInfo.getUserNm());

			userDAO.updateUser(userVO);
		}
	}
}
