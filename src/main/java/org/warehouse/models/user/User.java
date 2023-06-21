package org.warehouse.models.user;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class User{
	private String userId;
	private String userPw;

	private String userNm;
	private String userType;
	private String clntCd;
	private String custCtrCd;
	private String custCd;
	private String position;
	private String tel;
	private String email;
}
