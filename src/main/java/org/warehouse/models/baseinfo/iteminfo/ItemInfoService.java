package org.warehouse.models.baseinfo.iteminfo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.warehouse.configs.models.mapper.ItemInfoDAO;
import org.warehouse.controllers.users.UserInfo;

@Service
@RequiredArgsConstructor
public class ItemInfoService {

	private final ItemInfoDAO itemInfoDAO;
	private final HttpSession session;

	public void itemInfoSave(ItemInfoVO itemInfoVO){
		UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

		System.out.println("service Insert :: " + itemInfoVO);
		itemInfoVO.setRegNm(userInfo.getUserNm());
		itemInfoDAO.insertItemInfo(itemInfoVO);
	}

	public void itemInfoUpdate(ItemInfoVO itemInfoVO){
		UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

		System.out.println("service Update :: " + itemInfoVO);
		itemInfoVO.setModNm(userInfo.getUserNm());
		itemInfoDAO.updateItemInfo(itemInfoVO);
	}
}

