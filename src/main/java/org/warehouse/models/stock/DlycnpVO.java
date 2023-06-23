package org.warehouse.models.stock;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DlycnpVO { // 일수불

    private LocalDateTime payDt;    // 수불일자

    private Long dlyCntNo;  // 순번

    private String itemCd;  // 상품코드

    private String clntCd;  // 고객사코드

    private String locCd;   // 로케이션코드

    private String waCtrCd; // 물류센터코드

    private Long preDtStock;    // 전일재고

    private Long preDtFaultStock;   // 전일불량재고

    private Long normalIn;   // 정상입고

    private Long faultIn;   // 불량입고

    private Long relsAmt;   // 출고수량

    private Long moveAmt;   // 이동수량

    private Long amtAdj;    // 재고조정

    private Long normalStock;   // 정상재고

    private Long faultStock;    // 불량재고

    private String remk;    // 비고

    private LocalDateTime regDt;    // 등록일

    private String regNm;   // 등록자

    private LocalDateTime modDt;    // 수정일

    private String modNm;   // 수정자

    private String delYn;   // 삭제여부
}
