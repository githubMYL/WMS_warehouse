package org.warehouse.services;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.warehouse.configs.models.mapper.WactrDAO;
import org.warehouse.controllers.users.UserInfo;
import org.warehouse.models.user.UserVO;
import org.warehouse.models.wactr.WactrForm;
import org.warehouse.models.wactr.WactrVO;

@Service
@RequiredArgsConstructor
public class WactrRegisterService {
	private final WactrDAO wactrDAO;
	private final HttpSession session;

	public void register(WactrForm wactrForm) {
		UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

		WactrVO wactrVO = new ModelMapper().map(wactrForm, WactrVO.class);

		if(wactrForm.getFlag() == null) {	// 수정 상태가 아닌 등록상태인 경우
			if(wactrVO.getTel().contains("-") || wactrVO.getTel().contains(" ")) {
				wactrVO.setTel(wactrVO.getTel().replace("-", ""));
				wactrVO.setTel(wactrVO.getTel().replace(" ", ""));
			}
			wactrVO.setRemark(wactrForm.getRemk());
			wactrVO.setRegNm(userInfo.getUserNm());

			wactrDAO.insertWactr(wactrVO);
		} else {	// 수정상태인 경우
			if(wactrVO.getTel().contains("-") || wactrVO.getTel().contains(" ")) {
				wactrVO.setTel(wactrVO.getTel().replace("-", ""));
				wactrVO.setTel(wactrVO.getTel().replace(" ", ""));
			}
			wactrVO.setRemark(wactrForm.getRemk());
			wactrVO.setModNm(userInfo.getUserNm());

			wactrDAO.updateWactr(wactrVO);
		}
	}
}
