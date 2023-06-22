package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.clnt.Clnt;

import java.util.List;

@Mapper
public interface ClntMapper {
	List<Clnt> getClntList();
}
