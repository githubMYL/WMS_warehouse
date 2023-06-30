package org.warehouse.models.stdin;

import lombok.Data;
import org.warehouse.models.BaseEntity;

import java.time.LocalDate;

@Data
public class StdinVO extends BaseEntity {
	private LocalDate stdinDt;
	private Long stdinNum;
	private Long stdinNo;
	private String clntCd;
	private String status;

	private String itemCd;
	private String locCd;
	private String wactrCd;
	private Long beforeStdin;
	private Long normal;
	private Long fault;
	private Long stdinAfterAmt;
	private Long stdinFault;
}
