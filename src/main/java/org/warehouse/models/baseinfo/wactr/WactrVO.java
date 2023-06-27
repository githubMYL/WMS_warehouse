package org.warehouse.models.baseinfo.wactr;

import lombok.Data;
import org.warehouse.models.BaseEntity;

@Data
public class WactrVO extends BaseEntity {
	private String wactrCd;
	private String wactrNm;
	private String addr;
	private String tel;
	private String size;
	private String scale;
	private String reech;
	private String jackee;
	private String diesel;
	private String racNum;
	private String numInPer;
}
