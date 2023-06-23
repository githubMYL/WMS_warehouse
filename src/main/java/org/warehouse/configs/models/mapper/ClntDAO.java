package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.clnt.ClntVO;

import java.util.List;

@Mapper
public interface ClntDAO {
	List<ClntVO> getClntList();
}
