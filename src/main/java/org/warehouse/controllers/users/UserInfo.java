package org.warehouse.controllers.users;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.warehouse.commons.constraints.Role;

import java.util.Collection;

@Data @Builder
public class UserInfo implements UserDetails {
	private String userId;
	private String userPw;
	private String userNm;
	private Role userType;
	private String clntCd;
	private String custCtrCd;
	private String custCd;
	private String position;
	private String tel;
	private String email;

	private Collection<GrantedAuthority> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return userPw;
	}

	@Override
	public String getUsername() {
		return userId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
