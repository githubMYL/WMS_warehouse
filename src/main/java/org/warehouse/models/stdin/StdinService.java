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
		}
	}

	public void register(StdinForm[] stdinForms){
		System.out.println(stdinForms);
		System.out.println(stdinForms.length);
		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");

		//Form → StdinVO
		StdinVO[] stdinVOs = new StdinVO[stdinForms.length];

		for(int i = 0; i < stdinVOs.length; i++) {
			stdinVOs[i] = new ModelMapper().map(stdinForms[i], StdinVO.class);
		}

		if(stdinForms[0].getFlag() == "") {
			stdinVOs[0].setRemark(stdinForms[0].getRemark() != null ? stdinForms[0].getRemark() : "");
			stdinVOs[0].setRegNm(userInfo.getUserNm());

			//insert STDIN_H
			stdinDAO.insertHeaderStdin(stdinVOs[0]);

			System.out.println("stdin_h insert success");

			//insert STDIN_D
			for(int j = 0; j < stdinVOs.length; j++) {
				stdinVOs[j].setRemark(stdinForms[j].getRemark() != null ? stdinForms[j].getRemark() : "");
				stdinVOs[j].setRegNm(userInfo.getUserNm());

				//wactrCd Mapping
				ItemInfoVO item = itemInfoDAO.getItem(stdinVOs[j].getItemCd());
				stdinVOs[j].setWactrCd(item.getWactrCd());
				System.out.println("stdinVOs[j] : "+stdinVOs[j]);
				stdinDAO.insertDetailStdin(stdinVOs[j]);
			}
		} else {
			System.out.println("수정 탑니다!");
			for(int i = 0; i < stdinForms.length; i++) {
				if(stdinForms[i].isDelyn()) {
					stdinVOs[i].setDelyn("Y");
				} else {
					stdinVOs[i].setDelyn("N");
				}
			}
			stdinVOs[0].setRemark(stdinForms[0].getRemark());
			stdinVOs[0].setModNm(userInfo.getUserNm());

			//wactrCd Mapping
			ItemInfoVO item = itemInfoDAO.getItem(stdinVOs[0].getItemCd());
			stdinVOs[0].setWactrCd(item.getWactrCd());

			//insert STDIN_H
			stdinDAO.updateHeaderStdin(stdinVOs[0]);
			//insert STDIN_D
			stdinDAO.updateDetailStdin(stdinVOs[0]);
		}
	}
}
