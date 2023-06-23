package org.warehouse.models.stock;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TmstkVO {  // 시점재고

    private String itemCd;  // 상품코드

    private String clntCd;  // 고객사코드

    private String locCd;   // 로케이션코드

    private String wactrCd; // 물류센터코드

    private Long stockAmt;  // 재고수량

    private Long faultAmt;  // 불량재고

    private LocalDateTime stdinDt;  // 입고일자

    private String remk;    // 비고

    private LocalDateTime regDt;    // 등록일

    private String regNm;   // 등록자

    private LocalDateTime modDt;    // 수정일

    private String modNm;   // 수정자

    private String delYn;   // 삭제여부

}
