package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.custctr.CustCtrVO;

import java.util.List;

@Mapper
public interface CustCtrDAO {
	List<CustCtrVO> getCustCtrList();
}
