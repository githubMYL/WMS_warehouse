package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.admin.custctr.CustCtrVO;


import java.util.List;

@Mapper
public interface CustCtrDAO {
	void insertCustCtr(CustCtrVO custCtrVO);
	void updateCustCtr(CustCtrVO custCtrVO);
	void deleteCustCtr(String custCtrCd, String modNm);

	List<CustCtrVO> getCustCtrList();
	CustCtrVO getCustCtrByCd(String custCd, String custCtrCd);
	CustCtrVO getCustCtrByNm(String custCtrNm);
	List<CustCtrVO> getCustCtrListByCustCd(String custCd);
	CustCtrVO getCustCtrByCustCdCustCtrNm(String custCd, String custCtrNm);
	List<CustCtrVO> getListByCustNmCustCtrNm(String custNm, String custCtrNm);
}
