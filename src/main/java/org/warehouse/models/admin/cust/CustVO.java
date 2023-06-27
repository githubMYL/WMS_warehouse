package org.warehouse.models.admin.cust;

import lombok.Data;
import org.warehouse.models.BaseEntity;

@Data
public class CustVO extends BaseEntity {
	private String custCd;
	private String custNm;
	private String busiNum;
	private String custTel;
	private String ownerNm;
	private String busiAddr;

	// Join 컬럼
	private String custCtrCd;
	private String custCtrNm;
}
