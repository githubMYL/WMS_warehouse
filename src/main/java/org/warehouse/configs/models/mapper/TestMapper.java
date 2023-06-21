package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TestMapper {
	//select * from ~~
	public List<Map<String, Object>> SelectAllList() throws Exception;
}
