package org.warehouse.models.stock;

import lombok.Data;

@Data
public class StkadjForm {
	private String adj_dt;    // 조정일자
	private Long no;    // 순번
	private String clnt_cd;  // 고객사코드
	private String item_cd;  // 상품코드
	private String wactr_cd; // 물류센터코드
	private String loc_cd; // 로케이션코드

	private Long before_adj_stock;    // 조정 전 재고
	private Long after_adj_stock;    // 조정 후 재고
	private String adj_reason;   // 조정사유

	private String remk;    // 비고

	private String reg_dt;    // 등록일
	private String reg_nm;   // 등록자

	private String mod_dt;    // 수정일
	private String mod_nm;   // 수정자

	private String del_yn;   // 삭제여부



	// 정보 업데이트 위해 추가 변수
	private int modNomalAmt;
	private int modFaultAmt;

	private Long modTmstkStockAmt;
	private Long modTmstkFaultAmt;



	/////////// JOIN

	//rels_s
	private Long allo_amt;

	//tmstk
	private Long stock_amt;  // 재고수량
	private Long fault_amt;  // 불량재고

	// 리스트를 위해 조인 해온 컬럼
	private String wactr_nm;
	private String clnt_nm;
	private String item_nm;
}
