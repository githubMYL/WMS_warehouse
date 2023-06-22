package org.warehouse.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntity {
	String remark;
	LocalDateTime regDt;
	String regNm;
	LocalDateTime modDt;
	String modNm;
	boolean delyn;

	public void setDelyn(String delyn) {
		if(delyn.toUpperCase().equals("Y")) {
			this.delyn=true;
		} else {
			this.delyn=false;
		}
	}
}
