package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.stdin.StdinVO;
import org.warehouse.models.stock.StkadjVO;
import org.warehouse.models.stock.StktransfVO;
import org.warehouse.models.stock.TmstkVO;

import java.util.List;

@Mapper
public interface StockDAO {
	void insertStdin(StdinVO stdinVO);
	void updateStdin(StdinVO stdinVO);

	List<TmstkVO> tmstkList();
	List<TmstkVO> getSearchList(String search_clntNm, String search_itemCd ,String search_itemNm);

	TmstkVO getTmstkByConditions(String wactrCd, String clntCd, String itemCd, String locCd);


	TmstkVO getTmstkByLocCdItemCd(String locCd, String itemCd);
	List<StkadjVO> stkadjList();

	void insertTmstk(TmstkVO tmstkVO);
	void updateTmstk(TmstkVO tmstkVO);
	void deleteTmstk(TmstkVO tmstkVO);

	void insertStktransf(StktransfVO stktransfVO);
	List<StktransfVO> getStktransfList();
}
