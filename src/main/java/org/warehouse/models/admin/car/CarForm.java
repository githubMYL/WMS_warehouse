package org.warehouse.models.admin.car;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.warehouse.models.BaseEntity;

import java.time.LocalDate;

@Data
public class CarForm extends BaseEntity {
	private String carCd;
	@NotBlank
	private String carNum;

	private String deliveryArea;
	@NotBlank
	private String driverNm;
	private String driverTel;
	private String driverAddr;

	private String carType;
	private String carTon;

	private Long loadPlt;

	private boolean licenseYn;
	private LocalDate joinDt;
}
