package org.warehouse.models.baseinfo.coninfo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.warehouse.models.BaseEntity;

import java.time.LocalDate;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class ConInfoVO extends BaseEntity { // 계약정보

	private String clntCd;  	// 고객사코드

	private String clntNm;		// 고객사 명

	@Max(value = 1000, message = "1000개 이하만 가능합니다.")
	private Long mMin;  	// 월 미니멈

	private Long pltFee;    // 파렛트 보관료

	private LocalDate transSdt; // 거래시작일

	private LocalDate transEdt; // 거래만료일

}