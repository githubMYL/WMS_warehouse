package org.warehouse.models.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.warehouse.configs.models.mapper.UserDAO;
import org.warehouse.models.user.UserVO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserDAO userDAO;
	private final PasswordEncoder passwordEncoder;

	public List<UserVO> getUserList() {
		return userDAO.getUserList();
	}

	public UserVO getUserById(String userId) {
		UserVO userVO = userDAO.getUserById(userId);
		System.out.println(userVO);
		System.out.println("modDt: " + userVO.getModDt());
		return userVO;
	}

	public UserVO getUserByEmail(String email) {
		return userDAO.getUserByEmail(email);
	}

}
