package org.warehouse.models.admin.clnt;

import lombok.Data;
import org.warehouse.models.BaseEntity;

@Data
public class ClntVO extends BaseEntity {
	private String clntCd;
	private String clntNm;
	private String salesNm;
	private String salesTel;
	private String tplNm;
	private String tplTel;
	private String tplEmail;
	private String business;
	private String event;
	private String busiNum;
	private String ownerNm;
	private String ownerTel;
	private String busiAddr;
}
