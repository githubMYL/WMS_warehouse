package org.warehouse.controllers.admins;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.warehouse.models.user.UserVO;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class JoinForm {
	@NotBlank
	@Size(min=6)
	private String userId;
	@NotBlank
	private String userPw;
	@NotBlank
	private String userPwRe;

	private String userNm;
	private String userType;

	private String clntCd;
	private String custCtrCd;
	private String custCtrNm;
	private String custCd;
	private String custNm;
	private String position;
	@Size(max = 11)
	private String tel;
	private String email;

	public static UserVO of(JoinForm joinForm) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setSkipNullEnabled(true);
		return joinForm == null ? null : modelMapper.map(joinForm, UserVO.class);
	}

	private String flag;
}
