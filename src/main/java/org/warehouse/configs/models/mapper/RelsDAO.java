package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.rels.relsAction.RelsVO;

import java.util.List;

@Mapper
public interface RelsDAO {

    List<RelsVO> relsList();    // 출고등록 리스트
    List<RelsVO> relsDetailList(); // 출고등록 품목기준 레스트
    List<RelsVO> relsSubDetailList(); // 출곡등록 로케이션기준 리스트
}

