package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.stock.DlycnpVO;

import java.util.List;

@Mapper
public interface DlyCnpDAO {

    /** dlycno S*/
    void dlycnpScheduler(); // 일수불 스케쥴러

    DlycnpVO dlycnpVO();
    List<DlycnpVO> getDlyCnpList(DlycnpVO srchParam);

	/** dlycno E*/

}
