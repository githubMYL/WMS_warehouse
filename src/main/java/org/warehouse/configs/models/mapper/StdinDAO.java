package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.stdin.StdinVO;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface StdinDAO {
	void insertHeaderStdin(StdinVO stdinVO);
	void insertDetailStdin(StdinVO stdinVO);

	void updateHeaderStdin(StdinVO stdinVO);
	void updateDetailStdin(StdinVO stdinVO);

	void deleteHeaderStdin(String stdinNum);
	void deleteDetailStdin(String stdinNum);

	List<StdinVO> getList();
	List<StdinVO> getDetailList();
	List<StdinVO> getDetailListByConditions(LocalDate stdinDt, String clntNm, String itemCd, String itemNm);
	StdinVO[] getDetail(String stdinNum);
	StdinVO getDetailByNumNo(Long stdinNum, Long stdinNo);

	List<StdinVO> getListByConditions(LocalDate stdinDt, String clntNm, String status);

	void confirmItems(String stdinNum);
}
