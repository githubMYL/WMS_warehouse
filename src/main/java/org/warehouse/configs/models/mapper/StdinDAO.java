package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.stdin.StdinVO;

import java.util.List;

@Mapper
public interface StdinDAO {
	void insertHeaderStdin(StdinVO stdinVO);
	void insertDetailStdin(StdinVO stdinVO);
	List<StdinVO> getList();
	List<StdinVO> getDetailList();
	StdinVO getDetail(Long stdinNum);
}
