package org.warehouse.models.admin.car;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
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

	private Boolean licenseYn;
	@Past
	private LocalDate joinDt;

	private String flag;
}
