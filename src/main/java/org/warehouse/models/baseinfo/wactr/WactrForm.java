package org.warehouse.models.baseinfo.wactr;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WactrForm {
	@NotBlank
	private String wactrCd;
	@NotBlank
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
	private String remk;

	private String flag;
}
