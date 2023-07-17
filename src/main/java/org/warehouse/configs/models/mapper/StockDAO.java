package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.stdin.StdinVO;
import org.warehouse.models.stock.StkadjForm;
import org.warehouse.models.stock.StkadjVO;
import org.warehouse.models.stock.StktransfVO;
import org.warehouse.models.stock.TmstkVO;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface StockDAO {
	/* stdin S */
	void insertStdin(StdinVO stdinVO);
	void updateStdin(StdinVO stdinVO);
	/* stdin E */

	/* tmstk S */
	void insertTmstk(TmstkVO tmstkVO);
	void updateTmstkAmt(TmstkVO tmstkVO);
	void deleteTmstk(TmstkVO tmstkVO);

	List<TmstkVO> tmstkList();
	List<TmstkVO> getSearchList(String search_clntNm, String search_itemCd ,String search_itemNm);

	TmstkVO getTmstkByConditions(String wactrCd, String clntCd, String itemCd, String locCd);
	TmstkVO getTmstkByLocCdItemCd(String locCd, String itemCd);
	/* tmstk E */

	/* ====================================================================================== */

	/* stkadj S */

	// stkadj조인해서 tmstk list 가져오기
	List<StkadjForm> stkadjList();
	List<StkadjForm> search_stkadjList(String search_tmstk_wactrNm,
									   String search_tmstk_clntNm,
									   String search_tmstk_locCd,
									   String search_tmstk_itemNm);

	// 재고조정 더블클릭 해서 하나 가져오기
	StkadjForm stkadjOne(HashMap<String, String> tmstkCdMap);


	// StkadjModService
	// 재고조정 테이블 저장
	void insertStkadj(StkadjForm stkadjForm);

	// 시점재고 update
	void updateTmstk(StkadjForm stkadjForm);



	//조정내역을 위한 RestController 에 쓰임
	List<StkadjVO> getDetail(HashMap<String, String> tmstkCdMap);

	// 조정일자 검색
	List<StkadjVO> getSearch(HashMap<String,String> searchArray);
	/* stkadj E */

	/* ====================================================================================== */

	/* stktransf S */
	void insertStktransf(StktransfVO stktransfVO);
	List<StktransfVO> getStktransfList();
	/* stktransf E */
}
