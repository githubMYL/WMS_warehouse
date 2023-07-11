package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.baseinfo.iteminfo.ItemInfoVO;


import java.util.List;

@Mapper
public interface ItemInfoDAO {
    void insertItemInfo(ItemInfoVO itemInfoVO);             // 상품정보 저장
    void updateItemInfo(ItemInfoVO itemInfoVO);             // 상품정보 수정
    List<ItemInfoVO> getCodeList();                         // 관리단위 코드 가져오기
    ItemInfoVO getItemEqualsChk(ItemInfoVO itemInfoVO);     // 상품코드 중복확인
    List<ItemInfoVO> getItemList();                         // 입고에서 사용하는 리스트
    List<ItemInfoVO> getItemListSrch(ItemInfoVO params);    // 상품 전체 목록 가져오기
    List<ItemInfoVO> getItemListByClntCd(String clntCd);    // 고객사에 따른 상품 리스트 가져오기
    ItemInfoVO getItem(String itemCd);                      // 상품 코드에 따른 상품정보 가져오기
    void deleteItem(String keyVal);                         // 삭제여부 갱신
    ItemInfoVO getKeyCd(String keyVal);                     // 상품정보 수정
}
