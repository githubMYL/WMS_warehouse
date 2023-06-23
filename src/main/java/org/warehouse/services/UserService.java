package org.warehouse.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
import org.warehouse.configs.models.mapper.UserMapper;
import org.warehouse.models.user.User;
=======
import org.warehouse.configs.models.mapper.UserDAO;
import org.warehouse.models.user.UserVO;
>>>>>>> 0bb7b9657971b9bf3f3ac1a2255b34ce7ecc6707

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
<<<<<<< HEAD
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	public List<User> getUserList() {
		return userMapper.getUserList();
	}

	public User getUserById(String userId) {
		return userMapper.getUserById(userId);
	}

	public User getUserByEmail(String email) {
		return userMapper.getUserByEmail(email);
	}

	public void register(User user) {
		user.setUserPw(passwordEncoder.encode(user.getUserPw()));
		userMapper.insertUser(user);
=======
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
>>>>>>> 0bb7b9657971b9bf3f3ac1a2255b34ce7ecc6707
	}

}
