package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.stdin.StdinVO;
import org.warehouse.models.stock.TmstkVO;

import java.util.List;

@Mapper
public interface StockDAO {
	void insertStdin(StdinVO stdinVO);

	List<TmstkVO> tmstkList();
	List<TmstkVO> getSearchList(String search_clntNm,String search_itemCd ,String search_itemNm);
}
