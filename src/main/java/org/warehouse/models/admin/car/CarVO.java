package org.warehouse.models.admin.car;

import lombok.Data;
import org.warehouse.models.BaseEntity;

import java.time.LocalDate;

@Data
public class CarVO extends BaseEntity {
	private String carCd;
	private String carNum;
	private String deliveryArea;
	private String driverNm;
	private String driverTel;
	private String driverAddr;
	private String carType;
	private String carTon;
	private Long loadPlt;
	private boolean licenseYn;
	private LocalDate joinDt;
}
