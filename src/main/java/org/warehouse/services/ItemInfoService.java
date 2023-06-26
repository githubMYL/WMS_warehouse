package org.warehouse.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.warehouse.configs.models.mapper.ItemInfoDAO;
import org.warehouse.models.baseinfoVO.ItemInfoVO;

@Service
@RequiredArgsConstructor
public class ItemInfoService {

    private final ItemInfoDAO itemInfoDAO;

    public void itemInfoSave(ItemInfoVO itemInfoVO){

        System.out.println("service :: " + itemInfoVO);
        itemInfoDAO.insertItemInfo(itemInfoVO);
    }

}
