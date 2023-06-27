package org.warehouse.models.stdin;

import lombok.Data;
import org.warehouse.models.BaseEntity;

import java.time.LocalDate;

@Data
public class StdinVO extends BaseEntity {
	private LocalDate stdinDt;
	private Long stdinNum;
	private String clntCd;
	private String status;
}
