package org.warehouse.models.baseinfo.iteminfo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.warehouse.configs.models.mapper.ItemInfoDAO;

@Service
@RequiredArgsConstructor
public class ItemInfoService {

	private final ItemInfoDAO itemInfoDAO;

	public void itemInfoSave(ItemInfoVO itemInfoVO){


		System.out.println("service :: " + itemInfoVO);
		itemInfoDAO.insertItemInfo(itemInfoVO);
	}
}
