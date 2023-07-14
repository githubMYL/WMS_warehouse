package org.warehouse.models.baseinfo.coninfo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.warehouse.configs.models.mapper.ConInfoDAO;

@Service
@RequiredArgsConstructor
public class ConInfoService {

	private final ConInfoDAO conInfoDAO;

//    public List<ConInfoVO>getConinfo() {
//        return conInfoDAO.getConInfoList();
//    }

	public void conInfoSave(ConInfoVO conInfoVO){

//		System.out.println("Service :: clntCd :: " + conInfoVO.getClntCd());
//		System.out.println("Service :: mMin :: " + conInfoVO.getMMin());
//		System.out.println("Service :: pltFee :: " + conInfoVO.getPltFee());
//		System.out.println("Service :: transSdt :: " + conInfoVO.getTransSdt());
//		System.out.println("Service :: transEdt :: " + conInfoVO.getTransEdt());

		if (conInfoVO.getNo() != null){
			conInfoVO.setModNm("session");
			conInfoDAO.update(conInfoVO);
		} else {
			conInfoVO.setRegNm("session");
			conInfoDAO.insertConInfo(conInfoVO);
		}
	}
}