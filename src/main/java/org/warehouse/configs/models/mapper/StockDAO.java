package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.stdin.StdinVO;

@Mapper
public interface StockDAO {
	void insertStdin(StdinVO stdinVO);
}
