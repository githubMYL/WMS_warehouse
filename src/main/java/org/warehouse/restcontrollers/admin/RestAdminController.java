package org.warehouse.restcontrollers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.warehouse.configs.models.mapper.UserDAO;
import org.warehouse.models.user.UserVO;

import java.util.List;

@RestController
@RequestMapping("/admin/ajaxadmin")
@RequiredArgsConstructor
public class RestAdminController {
	private final UserDAO userDAO;

	@GetMapping("userSearch")
	public List<UserVO> userSearch(String clntNm, String userId, String userNm, String tel) {
		System.out.println(clntNm + ", " + userId + ", " + userNm + ", " + tel);
		return userDAO.getListByConditions(clntNm, userId, userNm, tel);
	}
}
