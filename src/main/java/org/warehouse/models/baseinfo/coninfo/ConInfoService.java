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

		if (conInfoVO.getNo() != null){
			conInfoVO.setModNm("session");
			conInfoDAO.update(conInfoVO);
		} else {
			conInfoVO.setRegNm("session");
			conInfoDAO.insertConInfo(conInfoVO);
		}
	}
}