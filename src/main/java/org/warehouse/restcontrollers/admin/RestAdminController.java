package org.warehouse.restcontrollers.admin;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.warehouse.configs.models.mapper.ClntDAO;
import org.warehouse.configs.models.mapper.CustCtrDAO;
import org.warehouse.configs.models.mapper.CustDAO;
import org.warehouse.configs.models.mapper.UserDAO;
import org.warehouse.controllers.users.UserInfo;
import org.warehouse.models.admin.clnt.ClntVO;
import org.warehouse.models.admin.cust.CustVO;
import org.warehouse.models.admin.custctr.CustCtrVO;
import org.warehouse.models.user.UserVO;

import java.util.List;

@RestController
@RequestMapping("/admin/ajaxadmin")
@RequiredArgsConstructor
public class RestAdminController {
	private final UserDAO userDAO;
	private final ClntDAO clntDAO;
	private final CustDAO custDAO;
	private final CustCtrDAO custCtrDAO;

	private final HttpSession session;

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
		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");

		String[] clntCdList = clntCd.split(",");

		for(int i = 0; i < clntCdList.length; i++) {
			clntDAO.deleteClnt(clntCdList[i], userInfo.getUserNm());
		}
	}
	/** ClntManage E */


	/** CustManage S */
	@GetMapping("custSearch")
	public List<CustVO> custSearch(String custNm) {
		return custDAO.getCustListByCustNm(custNm);
	}

	@GetMapping("custDelete")
	public void custDelete(String custCd) {
		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
		String[] custCdList = custCd.split(",");

		for(int i = 0; i < custCdList.length; i++) {
			custDAO.deleteCust(custCdList[i], userInfo.getUserNm());
		}
	}
	/** CustManage E */


	/** CustCtrManage S */
	@GetMapping("custCtrSearch")
	public List<CustCtrVO> custCtrSearch(String custNm, String custCtrNm) {
		return custCtrDAO.getListByCustNmCustCtrNm(custNm, custCtrNm);
	}

	@GetMapping("custCtrDelete")
	public void custCtrDelete(String custCd, String custCtrCd) {
		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
		String[] custCtrCdList = custCtrCd.split(",");

		for(int i = 0; i < custCtrCdList.length; i++) {
			custCtrDAO.deleteCustCtr(custCtrCdList[i], userInfo.getUserNm());
		}
	}
	/** CustCtrManage E */
}
