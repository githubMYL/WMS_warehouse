package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.cust.CustVO;

import java.util.List;

@Mapper
public interface CustDAO {
	List<CustVO> getCustList();
	CustVO getCustByCustNm(String custNm);
}
