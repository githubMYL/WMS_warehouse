package org.warehouse.models.custctr;

import lombok.Data;
import org.warehouse.models.BaseEntity;

@Data
public class CustCtrVO extends BaseEntity {
	private String custCtrCd;
	private String custCd;
	private String custCtrNm;
	private String custCtrAddr;
	private String custCtrTel;
	private String manager;
}
