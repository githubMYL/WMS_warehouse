package org.warehouse.models.stdin;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.warehouse.models.BaseEntity;

import java.time.LocalDate;

@Data
public class StdinForm extends BaseEntity {
	@NotNull @Future
	private LocalDate stdinDt;
	@NotBlank
	private String clntCd;
	private String clntNm;

	private String status;

	@NotBlank
	private String itemCd;
	private String itemNm;
	@NotBlank
	private String locCd;

	private Long beforeStdin;
	private Long normal;
	private Long fault;

	private String flag;	// register, update 구분용
}
