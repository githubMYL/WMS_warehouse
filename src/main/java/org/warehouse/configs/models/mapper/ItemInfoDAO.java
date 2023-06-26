package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.baseinfoVO.ItemInfoVO;

@Mapper
public interface ItemInfoDAO {

    void insertItemInfo(ItemInfoVO itemInfoVO);
}
