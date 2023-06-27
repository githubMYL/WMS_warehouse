package org.warehouse.models.baseinfo.iteminfo;

import lombok.*;
import org.warehouse.models.BaseEntity;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemInfoVO extends BaseEntity {

    private String waCtrCd; // 물류센터코드
    private String clntCd; // 고객사코드
    private String itemCd; // 상품코드
    private String itemNm; // 상품명
    private String locCd; // 로케이션코드
    private String boxUnit; // 관리단위
    //private Number getBox; // 박스당 입수
    private Long pltInBox; // 파렛트당 박스 수
    private String remk; // 비고

    /** 관리단위 코드 S */
    private String itemCode; // 단위 코드
    private String itemValue; // 단위 명
    /** 관리단위 코드 E */

}
