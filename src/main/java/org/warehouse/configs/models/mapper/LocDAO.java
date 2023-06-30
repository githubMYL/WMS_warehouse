package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.baseinfo.loc.LocVO;

import java.util.List;

@Mapper
public interface LocDAO {



	List<LocVO> getLocList(); //로케이션 리스트
	List<LocVO> getSearchList(String search_loc); // 로케이션 검색 리스트
	void insertLoc(LocVO loc); //로케이션 등록

	void deleteLoc(String loc_cd); // 로케이션 삭제

	LocVO modLoc(String loc_cd_array); // 수정

}