package org.warehouse.models.stdin;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.warehouse.models.BaseEntity;

import java.time.LocalDate;

@Data
public class StdinForm extends BaseEntity {
	@NotBlank
	private LocalDate stdinDt;
	@NotBlank
	private String clntCd;
	@NotBlank
	private String clntNm;

	private String status;

	@NotBlank
	private String itemCd;
	@NotBlank
	private String itemNm;
	@NotBlank
	private String location;
	private String normal;
	private String fault;

}
