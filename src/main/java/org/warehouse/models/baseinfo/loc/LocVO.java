package org.warehouse.models.baseinfo.loc;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.warehouse.models.BaseEntity;

@Data
@EqualsAndHashCode(callSuper=false)
public class LocVO extends BaseEntity {

	String loc_cd;
	@NotBlank
	String wactr_cd;
	@NotBlank
	String loc_nm;
	@NotBlank
	String loc_addr;

	String remk;
	String reg_nm;
	String mod_nm;


	// 로케이션 코드가 바뀔것을 대비해 수정전 코드가져오기
	String ori_loc_cd;

	//wactr_cd따라서 조인걸어 wactrNm 얻어오기
	String wactr_nm;
}