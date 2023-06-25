package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.wactr.WactrVO;

import java.util.List;

@Mapper
public interface WactrDAO {
	void insertWactr(WactrVO wactrVO);
	WactrVO getWactrByCd(String wactrCd);
	WactrVO getWactrByNm(String wactrNm);
}
