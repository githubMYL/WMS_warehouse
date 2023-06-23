package org.warehouse.controllers.users;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.warehouse.configs.models.mapper.UserDAO;
import org.warehouse.models.user.UserVO;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInfoService implements UserDetailsService {
	private final UserDAO userDAO;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserVO userVO = userDAO.getUserById(username);
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
				.authorities(authorities)
				.build();
	}
}