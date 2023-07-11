package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.admin.clnt.ClntStdinVO;

import java.util.List;

@Mapper
public interface ClntStdinDAO {
	List<ClntStdinVO> getList();
	void updateStatus();
}
