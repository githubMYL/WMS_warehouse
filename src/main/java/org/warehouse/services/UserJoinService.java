package org.warehouse.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.warehouse.configs.models.mapper.UserMapper;
import org.warehouse.controllers.admins.JoinForm;
import org.warehouse.models.user.User;

@Service
@RequiredArgsConstructor
public class UserJoinService {
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	public void join(JoinForm joinForm) {
		User user = new ModelMapper().map(joinForm, User.class);

		user.setUserPw(passwordEncoder.encode(joinForm.getUserPw()));

		userMapper.insertUser(user);
	}

}
