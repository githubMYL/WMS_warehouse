package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.wactr.WactrVO;

import java.util.List;

@Mapper
public interface WactrDAO {
    void insertWactr(WactrVO wactrVO);
    void updateWactr(WactrVO wactrVO);
    void deleteWactr(String wactrCd);
    WactrVO getWactrByCd(String wactrCd);
    WactrVO getWactrByNm(String wactrNm);

    List<WactrVO> getListByNmScale(String wactrNm, String scale);
}