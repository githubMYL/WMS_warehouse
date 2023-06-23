package org.warehouse.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.warehouse.configs.models.mapper.UserMapper;
import org.warehouse.models.user.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
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
	}

}
