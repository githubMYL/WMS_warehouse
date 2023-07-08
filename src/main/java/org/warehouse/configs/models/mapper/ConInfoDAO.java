package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.baseinfo.coninf.ConInfoVO;

import java.util.List;

@Mapper
public interface ConInfoDAO {

	void insertConInfo(ConInfoVO conInfo); // 계약정보

	ConInfoVO getClntCd();

	ConInfoVO getTranSdt();

	List<ConInfoVO> getConListSearch(ConInfoVO srchParam);
}