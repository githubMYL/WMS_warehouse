package org.warehouse.models.stdin;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.warehouse.configs.models.mapper.ItemInfoDAO;
import org.warehouse.configs.models.mapper.StdinDAO;
import org.warehouse.configs.models.mapper.StockDAO;
import org.warehouse.controllers.users.UserInfo;
import org.warehouse.models.baseinfo.iteminfo.ItemInfoVO;

@Service
@RequiredArgsConstructor
public class StdinService {
	private final StdinDAO stdinDAO;
	private final HttpSession session;
	private final ItemInfoDAO itemInfoDAO;
	private final StockDAO stockDAO;

	public void register(StdinForm stdinForm) {
		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");

		//Form → StdinVO
		StdinVO stdinVO = new ModelMapper().map(stdinForm, StdinVO.class);

		if(stdinForm.getFlag() == null) {
			stdinVO.setRemark(stdinForm.getRemark());

			stdinVO.setRegNm(userInfo.getUserNm());

			//wactrCd Mapping
			ItemInfoVO item = itemInfoDAO.getItem(stdinVO.getItemCd());
			stdinVO.setWactrCd(item.getWactrCd());

			//insert STDIN_H
			stdinDAO.insertHeaderStdin(stdinVO);
			//insert STDIN_D
			stdinDAO.insertDetailStdin(stdinVO);
		} else {
			if(stdinForm.isDelyn()) {
				stdinVO.setDelyn("Y");
			} else {
				stdinVO.setDelyn("N");
			}

			stdinVO.setRemark(stdinForm.getRemark());
			stdinVO.setModNm(userInfo.getUserNm());

			//wactrCd Mapping
			ItemInfoVO item = itemInfoDAO.getItem(stdinVO.getItemCd());
			stdinVO.setWactrCd(item.getWactrCd());

			System.out.println(stdinVO);
			//insert STDIN_H
			stdinDAO.updateHeaderStdin(stdinVO);
			//insert STDIN_D
			stdinDAO.updateDetailStdin(stdinVO);

			if(stdinVO.getStatus().equals("2")) {
				//insert TMSTK
				System.out.println("변경됩니다!");
				stdinVO.setRegNm(userInfo.getUserNm());
				stockDAO.insertStdin(stdinVO);
			}
		}
	}
}
