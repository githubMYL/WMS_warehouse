package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.baseinfo.coninfo.ConInfoVO;

@Mapper
public interface ConInfoDAO {
	//List<ConInfoVO> getConInfoList();

	void insertConInfo(ConInfoVO conInfo); // 계약정보

	ConInfoVO getClntCd();

	ConInfoVO getTranSdt();
}