package org.warehouse.models.admin.custctr;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.warehouse.configs.models.mapper.CustCtrDAO;
import org.warehouse.configs.models.mapper.CustDAO;
import org.warehouse.controllers.users.UserInfo;
import org.warehouse.models.admin.cust.CustForm;
import org.warehouse.models.admin.cust.CustVO;

@Service
@RequiredArgsConstructor
public class CustCtrService {
	private final HttpSession session;
	private final CustCtrDAO custCtrDAO;

	public void register(CustCtrForm custCtrForm) {
		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");

		CustCtrVO custCtrVO = new ModelMapper().map(custCtrForm, CustCtrVO.class);

		if(custCtrForm.getFlag() == null) {
			custCtrVO.setRegNm(userInfo.getUserNm());

			custCtrDAO.insertCustCtr(custCtrVO);
		} else {
			// 수정부분
			custCtrVO.setModNm(userInfo.getUserNm());

			custCtrDAO.updateCustCtr(custCtrVO);
		}
	}
}
