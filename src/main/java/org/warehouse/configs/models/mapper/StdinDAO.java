package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.stdin.StdinVO;

@Mapper
public interface StdinDAO {
	void insertStdin(StdinVO stdinVO);
}
