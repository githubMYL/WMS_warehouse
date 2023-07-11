package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.rels.relsAction.RelsVO;

import java.util.List;

@Mapper
public interface RelsDAO {

    List<RelsVO> relsList();    // 출고등록 헤더 리스트
}

