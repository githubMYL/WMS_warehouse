package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.baseinfo.iteminfo.ItemInfoVO;


import java.util.List;

@Mapper
public interface ItemInfoDAO {

    void insertItemInfo(ItemInfoVO itemInfoVO); // 상품정보 저장

    List<ItemInfoVO> getCodeList(); // 관리단위 코드 가져오기

    List<ItemInfoVO> getItemList();

    List<ItemInfoVO> getItemListByClntCd(String clntCd);
}