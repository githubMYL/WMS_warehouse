package org.warehouse.models.baseinfo.loc;

import lombok.Data;
import org.warehouse.models.BaseEntity;

@Data
public class LocVO extends BaseEntity {

	String loc_cd;
	String wactr_cd;
	String loc_nm;
	String loc_addr;
	int rac_num;
	String remk;
	String reg_nm;
}
