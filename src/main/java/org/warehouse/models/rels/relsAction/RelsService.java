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
        System.out.println("relsVO.getRelsNo() ::::: " + relsVO.getRelsNo());
        if(relsVO.getRelsNo() == null){
            RelsVO getRelsNo = relsDAO.relsNoChk(relsVO);
            System.out.println("relsNo  ::  " + getRelsNo.getRelsNo());

//            if(relsVO.getDeliRequestDt() == null)
//                relsVO.setDeliRequestDt();

            relsVO.setRelsNo(getRelsNo.getRelsNo());
            relsVO.setRegNm("session");
            System.out.println("service SAVE :::::::::: " + relsVO);
            /** 출고등록 헤더 insert */
            relsDAO.relsHinsert(relsVO);
            /** 출고등록 상세 insert */
            relsDAO.relsDinsert(relsVO);
        }else {
            relsDAO.relsDinsert(relsVO);
        }

    }
}
