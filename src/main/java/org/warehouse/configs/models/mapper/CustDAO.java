package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.admin.cust.CustVO;


import java.util.List;

@Mapper
public interface CustDAO {
	void insertCust(CustVO custVO);
	void updateCust(CustVO custVO);
	void deleteCust(String custCd, String modNm);

	List<CustVO> getCustList();
	CustVO getCustByCustNm(String custNm);
	List<CustVO> getCustListByCustNm(String custNm);
	CustVO getCustByCd(String custCd);
}
