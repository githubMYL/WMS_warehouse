package org.warehouse.models.stock;

import lombok.Data;
import org.warehouse.models.BaseEntity;

import java.time.LocalDateTime;

@Data
public class TmstkVO extends BaseEntity {  // 시점재고

    private String itemCd;  // 상품코드

    private String clntCd;  // 고객사코드

    private String locCd;   // 로케이션코드

    private String wactrCd; // 물류센터코드

    private Long stockAmt;  // 재고수량

    private Long faultAmt;  // 불량재고

    private LocalDateTime stdinDt;  // 입고일자


    // 리스트를 위해 조인 해온 컬럼
    private String wactr_nm;
    private String clnt_nm;
    private String loc_cd;
    private String item_cd;
    private String item_nm;
}
