package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.warehouse.models.admin.clnt.ClntVO;


import java.util.List;

@Mapper
public interface ClntDAO {
	List<ClntVO> getClntList();
	void insertClnt(ClntVO clntVO);
	void deleteClnt(String clntCd);
	void updateClnt(ClntVO clntVO);

	ClntVO getClntByCd(String clntCd);
	List<ClntVO> getClntListByNm(String clntNm);

	ClntVO getClntByCdAndNm(@Param("clntCd")String clntCd, @Param("clntNm")String clntNm); // 고객사 존재유무 확인
}
