package org.warehouse.models.stdin;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.warehouse.models.BaseEntity;

import java.time.LocalDate;

@Data
public class StdinForm extends BaseEntity {
	@NotBlank
	private LocalDate stdinDt;
	private Long stdinNum;
	@NotBlank
	private String clntCd;
	private String status;
}
