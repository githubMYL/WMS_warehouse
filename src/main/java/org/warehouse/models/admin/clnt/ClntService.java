package org.warehouse.models.admin.clnt;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.warehouse.configs.models.mapper.ClntDAO;
import org.warehouse.controllers.users.UserInfo;
import org.warehouse.models.stdin.StdinVO;

@Service
@RequiredArgsConstructor
public class ClntService {
	private final HttpSession session;
	private final ClntDAO clntDAO;

	public void register(ClntForm clntForm) {
		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");

		//Form → StdinVO
		ClntVO clntVO = new ModelMapper().map(clntForm, ClntVO.class);

		if(clntForm.getFlag() == null) {
			clntVO.setRegNm(userInfo.getUserNm());

			clntDAO.insertClnt(clntVO);
		} else {
			// 수정부분
			clntVO.setModNm(userInfo.getUserNm());

			clntDAO.updateClnt(clntVO);
		}
	}
}
