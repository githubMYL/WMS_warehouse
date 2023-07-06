package org.warehouse.models.admin.cust;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.warehouse.configs.models.mapper.ClntDAO;
import org.warehouse.configs.models.mapper.CustDAO;
import org.warehouse.controllers.users.UserInfo;
import org.warehouse.models.admin.clnt.ClntForm;
import org.warehouse.models.admin.clnt.ClntVO;

@Service
@RequiredArgsConstructor
public class CustService {
	private final HttpSession session;
	private final CustDAO custDAO;

	public void register(CustForm custForm) {
		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");

		//Form → StdinVO
		CustVO custVO = new ModelMapper().map(custForm, CustVO.class);

		if(custForm.getFlag() == null) {
			custVO.setRegNm(userInfo.getUserNm());

			custDAO.insertCust(custVO);
		} else {
			// 수정부분
			custVO.setModNm(userInfo.getUserNm());

			custDAO.updateCust(custVO);
		}
	}
}
