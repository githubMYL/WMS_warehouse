package org.warehouse.models.stock;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StkadjVO { // 재고조정

    private LocalDateTime adjDt;    // 조정일자

    private Long no;    // 순번

    private String clntCd;  // 고객사코드

    private String itemCd;  // 상품코드

    private String waCtrCd; // 물류센터코드

    private String locCd; // 로케이션코드

    private Long beforeAdjStock;    // 조정 전 재고

    private Long afterAdjStock;    // 조정 후 재고

    private String adjReason;   // 조정사유

    private String remk;    // 비고

    private LocalDateTime regDt;    // 등록일

    private String regNm;   // 등록자

    private LocalDateTime modDt;    // 수정일

    private String modNm;   // 수정자

    private String delYn;   // 삭제여부
}
