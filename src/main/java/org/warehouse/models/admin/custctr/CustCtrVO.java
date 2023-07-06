package org.warehouse.models.admin.custctr;

import lombok.Data;
import org.warehouse.models.BaseEntity;

@Data
public class CustCtrVO extends BaseEntity {
	private String custCd;
	private String custNm;

	private String custCtrCd;
	private String custCtrNm;
	private String custCtrAddr;
	private String custCtrTel;
	private String manager;
}
