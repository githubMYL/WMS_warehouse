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

		System.out.println(joinForm.getFlag());
		if(joinForm.getFlag() == null || joinForm.getFlag().isBlank() || joinForm.getFlag().equals("addPop")) {
			System.out.println("flag null");
			userVO.setUserPw(passwordEncoder.encode(joinForm.getUserPw()));
			if(custDAO.getCustByCustNm(joinForm.getCustNm()) != null){
				userVO.setCustCd(custDAO.getCustByCustNm(joinForm.getCustNm()).getCustCd());
			}
			if(custCtrDAO.getCustCtrByNm(joinForm.getCustCtrNm()) != null){
				userVO.setCustCtrCd(custCtrDAO.getCustCtrByNm(joinForm.getCustCtrNm()).getCustCtrCd());
			}
			if(userInfo != null){
				userVO.setRegNm(userInfo.getUserNm());
			}else{
				userVO.setRegNm("");
			}
			System.out.println("joinForm :: " + joinForm);

			userDAO.insertUser(userVO);
		} else {
			System.out.println("flag null222");
			userVO.setUserPw(passwordEncoder.encode(joinForm.getUserPw()));

			if(custDAO.getCustByCustNm(joinForm.getCustNm()) != null){
				userVO.setCustCd(custDAO.getCustByCustNm(joinForm.getCustNm()).getCustCd());
			}
			if(custCtrDAO.getCustCtrByNm(joinForm.getCustCtrNm()) != null){
				userVO.setCustCtrCd(custCtrDAO.getCustCtrByNm(joinForm.getCustCtrNm()).getCustCtrCd());
			}
			System.out.println("userInfo : " + userInfo);
			userVO.setModNm(userInfo.getUserNm());

			userDAO.updateUser(userVO);
		}
	}
}