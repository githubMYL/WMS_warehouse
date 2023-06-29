package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.baseinfo.iteminfo.ItemInfoVO;


import java.util.List;

@Mapper
public interface ItemInfoDAO {

    void insertItemInfo(ItemInfoVO itemInfoVO); // 상품정보 저장

    List<ItemInfoVO> getCodeList(); // 관리단위 코드 가져오기

    ItemInfoVO getItemEqualsChk(ItemInfoVO itemInfoVO); // 상품코드 중복확인

    ItemInfoVO getWactrByCdAndNm(ItemInfoVO itemInfoVO); // 물류센터 존재유무 확인
    ItemInfoVO getClntByCdAndNm(ItemInfoVO itemInfoVO); // 고객사 존재유무 확인
    ItemInfoVO getLocByCd(ItemInfoVO itemInfoVO); // 로케이션 존재유무 확인
}
