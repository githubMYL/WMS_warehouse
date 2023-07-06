package org.warehouse.restcontrollers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.warehouse.configs.models.mapper.ClntDAO;
import org.warehouse.configs.models.mapper.UserDAO;
import org.warehouse.models.admin.clnt.ClntVO;
import org.warehouse.models.user.UserVO;

import java.util.List;

@RestController
@RequestMapping("/admin/ajaxadmin")
@RequiredArgsConstructor
public class RestAdminController {
	private final UserDAO userDAO;
	private final ClntDAO clntDAO;

	/** UserManage S */
	@GetMapping("userSearch")
	public List<UserVO> userSearch(String clntNm, String userId, String userNm, String tel) {
		System.out.println(clntNm + ", " + userId + ", " + userNm + ", " + tel);
		return userDAO.getListByConditions(clntNm, userId, userNm, tel);
	}

	@GetMapping("userDelete")
	public void userDelete(String userId) {
		String[] userIdList = userId.split(",");

		for(int i = 0; i < userIdList.length; i++) {
			userDAO.deleteUser(userIdList[i]);
		}
	}
	/** UserManage E */


	/** ClntManage S */
	@GetMapping("clntSearch")
	public List<ClntVO> clntSearch(String clntNm) {
		return clntDAO.getClntListByNm(clntNm);
	}

	@GetMapping("clntDelete")
	public void clntDelete(String clntCd) {
		String[] clntCdList = clntCd.split(",");

		for(int i = 0; i < clntCdList.length; i++) {
			clntDAO.deleteClnt(clntCdList[i]);
		}
	}
	/** ClntManage E */
}
