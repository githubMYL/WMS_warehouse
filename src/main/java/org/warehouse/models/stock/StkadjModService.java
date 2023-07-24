package org.warehouse.models.stock;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.warehouse.configs.models.mapper.StockDAO;
import org.warehouse.controllers.users.UserInfo;
import org.warehouse.models.baseinfo.wactr.WactrVO;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class StkadjModService {

	private final StockDAO stockDAO;
	private final HttpSession session;


	public void stkadjMod(StkadjForm stkadjForm){
		System.out.println(stkadjForm.toString());

		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");

		stkadjForm.setMod_nm(userInfo.getUserNm());

		System.out.println("정상재고 차이" + stkadjForm.getModTmstkStockAmt());
		System.out.println("불량재고 차이" + stkadjForm.getModTmstkFaultAmt());

		stockDAO.insertStkadj(stkadjForm);
		stockDAO.updateTmstk(stkadjForm);
	}
}