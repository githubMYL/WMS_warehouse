package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.warehouse.models.baseinfo.loc.LocVO;

import java.util.List;

@Mapper
public interface LocDAO {
	List<LocVO> getLocList(); 						// 로케이션 리스트
	List<LocVO> getSearchList(String search_loc); 	// 로케이션 검색 리스트
	LocVO locCdVo(String loc_cd , String wactr_cd);					// loc_cd 에 따른 하나의 정보 조회
	List<LocVO> getLocListByItemCd(String itemCd);	// 상품코드에 따른 로케이션 리스트 가져오기
	void insertLoc(LocVO loc); 						//로케이션 등록
	void deleteLoc(String loc_cd , String wactr_cd); 					// 로케이션 삭제

	void modLoc(LocVO loc); 				// 수정
	LocVO getLocByCd(@Param("loc_cd")String locCd);	// 로케이션 코드에 따른 로케이션 정보 가져오기
}
