package org.warehouse.controllers.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.warehouse.models.user.User;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class JoinForm {
	@NotBlank
	@Size(min=6)
	private String userId;

	private String userPw;
	private String userPwRe;

	private String userNm;
	private String userType;

	private String clntCd;
	private String custCtrCd;
	private String custCd;
	private String position;
	private String tel;
	private String email;

	public static User of(JoinForm joinForm) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setSkipNullEnabled(true);
		return joinForm == null ? null : modelMapper.map(joinForm, User.class);
	}

}
