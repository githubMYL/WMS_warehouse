package org.warehouse.models.admin.clnt;

import lombok.Data;
import org.warehouse.models.BaseEntity;

@Data
public class ClntStdinVO extends BaseEntity {
	private String itemCd;
	private Long beforeStdin;
	private boolean status;

	// join
	private String wactrCd; 	// 물류센터코드
	private String clntCd; 		// 고객사코드
	private String locCd; 		// 로케이션코드

	private String clntNm;		// 고객사명
}
