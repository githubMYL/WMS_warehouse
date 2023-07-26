package org.warehouse.models.rels.relsAction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.warehouse.configs.models.mapper.RelsDAO;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RelsService {

    private final RelsDAO relsDAO;
    public void save(RelsVO relsVO){

        relsVO.setRegNm("session");

        if(relsVO.getRelsNo() == null){
            RelsVO getRelsNo = relsDAO.relsNoChk(relsVO);
            System.out.println("relsNo  ::  " + getRelsNo.getRelsNo());

            relsVO.setRelsNo(getRelsNo.getRelsNo());
            /** 출고등록 헤더 insert */
            relsDAO.relsHinsert(relsVO);
            /** 출고등록 상세 insert */
            relsDAO.relsDinsert(relsVO);
        }else {
            relsDAO.relsDinsert(relsVO);
        }
    }

    public void update(RelsVO relsVO){

        relsVO.setModNm("session");
        System.out.println("update =====");
        System.out.println(relsVO);
        System.out.println("update =====");

        relsDAO.relsUpdate(relsVO);
    }
}
