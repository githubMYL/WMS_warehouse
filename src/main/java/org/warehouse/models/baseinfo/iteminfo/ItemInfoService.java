package org.warehouse.models.baseinfo.iteminfo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.warehouse.configs.models.mapper.ItemInfoDAO;

@Service
@RequiredArgsConstructor
public class ItemInfoService {

	private final ItemInfoDAO itemInfoDAO;

	public void itemInfoSave(ItemInfoVO itemInfoVO){

		System.out.println("service Insert :: " + itemInfoVO);
		itemInfoDAO.insertItemInfo(itemInfoVO);
	}

	public void itemInfoUpdate(ItemInfoVO itemInfoVO){

		System.out.println("service Update :: " + itemInfoVO);
		itemInfoDAO.updateItemInfo(itemInfoVO);
	}
}