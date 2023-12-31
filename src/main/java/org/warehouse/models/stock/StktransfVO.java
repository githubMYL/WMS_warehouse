package org.warehouse.models.stock;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StktransfVO {  // 재고이동

    private LocalDateTime moveDt;   // 이동일자

    private Long no; // 순번

    private String itemNo;  // 상품코드

    private String clntCd;  // 고객사코드

    private String waCtrCdFrom; // 물류센터코드 From

    private String locCdFrom; // 로케이션코드 From

    private String waCtrCdTo; // 물류센터코드 To

    private String locCdTo; // 로케이션코드 To

    private Long runtimeStock;  // 시점가용재고

    private Long moveAmt;   // 이동수량

    private String reason;  // 사유

    private String remk;    // 비고

    private LocalDateTime regDt;    // 등록일

    private String regNm;   // 등록자

    private LocalDateTime modDt;    // 수정일

    private String modNm;   // 수정자

    private String delYn;   // 삭제여부

}
