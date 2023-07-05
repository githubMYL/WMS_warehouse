package org.warehouse.models.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.warehouse.configs.models.mapper.UserDAO;
import org.warehouse.controllers.admins.JoinForm;

@Service
@RequiredArgsConstructor
public class UserJoinService {
	private final UserDAO userDAO;
	private final PasswordEncoder passwordEncoder;

	public void join(JoinForm joinForm) {
		UserVO userVO = new ModelMapper().map(joinForm, UserVO.class);

		userVO.setUserPw(passwordEncoder.encode(joinForm.getUserPw()));

		System.out.println(userVO);

		userDAO.insertUser(userVO);
	}

}
