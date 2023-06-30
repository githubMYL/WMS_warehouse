package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.warehouse.models.baseinfo.wactr.WactrVO;

import java.util.List;

@Mapper
public interface WactrDAO {
	void insertWactr(WactrVO wactrVO);
	void updateWactr(WactrVO wactrVO);
	void deleteWactr(String wactrCd);
	WactrVO getWactrByCd(String wactrCd);
	WactrVO getWactrByNm(String wactrNm);
	List<WactrVO> getList();
	List<WactrVO> getListByNmScale(String wactrNm, String scale);

	WactrVO getWactrByCdAndNm(@Param("wactrCd")String wactrCd, @Param("wactrNm")String wactrNm);
}
