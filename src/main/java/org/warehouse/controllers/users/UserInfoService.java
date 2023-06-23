package org.warehouse.controllers.users;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
import org.warehouse.configs.models.mapper.UserMapper;
import org.warehouse.models.user.User;
=======
import org.warehouse.configs.models.mapper.UserDAO;
import org.warehouse.models.user.UserVO;
>>>>>>> 0bb7b9657971b9bf3f3ac1a2255b34ce7ecc6707

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInfoService implements UserDetailsService {
<<<<<<< HEAD
	private final UserMapper userMapper;
=======
	private final UserDAO userDAO;
>>>>>>> 0bb7b9657971b9bf3f3ac1a2255b34ce7ecc6707


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
<<<<<<< HEAD
		User user = userMapper.getUserById(username);
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}

		List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(user.getUserType()));

		return UserInfo.builder()
				.userId(user.getUserId())
				.userPw(user.getUserPw())
				.userNm(user.getUserNm())
				.userType(user.getUserType())
				.clntCd(user.getClntCd())
				.custCtrCd(user.getCustCtrCd())
				.custCd(user.getCustCd())
				.position(user.getPosition())
				.tel(user.getTel())
				.email(user.getEmail())
=======
		UserVO userVO = userDAO.getUserById(username);
		System.out.println(userVO);
		if(userVO == null) {
			throw new UsernameNotFoundException(username);
		}

		List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(userVO.getUserType().toString()));

		return UserInfo.builder()
				.userId(userVO.getUserId())
				.userPw(userVO.getUserPw())
				.userNm(userVO.getUserNm())
				.userType(userVO.getUserType())
				.clntCd(userVO.getClntCd())
				.custCtrCd(userVO.getCustCtrCd())
				.custCd(userVO.getCustCd())
				.position(userVO.getPosition())
				.tel(userVO.getTel())
				.email(userVO.getEmail())
>>>>>>> 0bb7b9657971b9bf3f3ac1a2255b34ce7ecc6707
				.authorities(authorities)
				.build();
	}
}