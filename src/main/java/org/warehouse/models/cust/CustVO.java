package org.warehouse.models.cust;

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
}
