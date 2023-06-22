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
		User user = userMapper.getUserById(userId);
		System.out.println(user);
		System.out.println("modDt: " + user.getModDt());
		return user;
	}

	public User getUserByEmail(String email) {
		return userMapper.getUserByEmail(email);
	}

}
