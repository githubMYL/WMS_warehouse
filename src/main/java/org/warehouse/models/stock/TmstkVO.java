package org.warehouse.models.stock;

import lombok.Data;
import org.warehouse.models.BaseEntity;

import java.time.LocalDateTime;
@Data
public class TmstkVO extends BaseEntity{  // 시점재고

    private String item_cd;  // 상품코드

    private String clnt_cd;  // 고객사코드

    private String loc_cd;   // 로케이션코드

    private String wactr_cd; // 물류센터코드

    private Long stock_amt;  // 재고수량

    private Long fault_amt;  // 불량재고

    private LocalDateTime stdin_dt;  // 입고일자


    // 리스트를 위해 조인 해온 컬럼
    private String wactr_nm;
    private String clnt_nm;
    private String item_nm;
}