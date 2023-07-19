package org.warehouse.models.rels.relsAction;

import lombok.*;
import org.warehouse.models.BaseEntity;

import java.time.LocalDate;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelsVO extends BaseEntity {
    /** rels_key */
    private LocalDate relsDt;   // H 출고일자
    private Long relsNo;        // H 출고번호
    private Long relsDNo;       // D 출고순번
    private Long alloNo;        // S 할당번호
    private String custCd;      // D 납품처코드
    private String custNm;          // 납품센터코드
    private String custCtrCd;   // D 납품센터코드
    private String custCtrNm;       // 납품센터명
    private String clntCd;      // H, D, S 고객사코드
    private String clntNm;          // 고객사명
    private String itemCd;      // D, S 상품코드
    private String itemNm;          // 상품명
    private String wactrCd;     // S 물류센터코드
    private String wactrNm;         // 물류창고명
    private String locCd;       // S 로케이션코드

    /** rels_h */
    private String status;      // H, D 진행상태
    private String remk;        // H, D, S 비고

    /** rels_d */
    private Long regInAmt;          // 출고지시수량
    private Long relsCnt;           // 출고수량
    private LocalDate confDt;       // 확정일자
    private LocalDate deliRequestDt;// 납품요청일
    private Long plt;               // 파렛트
    private Long box;               // 박스
    private Long relsAfterAmt;      // D, S 출고후수량

    /** rels_s */
    private Long alloAmt;           // 할당수량
    private Long relsAmt;           // 출고수량
    private Long alloAfterStock;    // 할당 후 재고
    
    private Long rowCnt;            // 순번
    private String dataYn;          // 데이터 존재유무
}