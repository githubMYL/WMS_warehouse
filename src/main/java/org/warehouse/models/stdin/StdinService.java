package org.warehouse.models.stdin;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.warehouse.configs.models.mapper.ItemInfoDAO;
import org.warehouse.configs.models.mapper.StdinDAO;
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

		StdinVO stdinVO = new ModelMapper().map(stdinForm, StdinVO.class);

		stdinVO.setRemark(stdinForm.getRemark());
		stdinVO.setRegNm(userInfo.getUserNm());

		System.out.println(stdinVO);

		ItemInfoVO item = itemInfoDAO.getItem(stdinVO.getItemCd());
		stdinVO.setWactrCd(item.getWactrCd());

		stdinDAO.insertHeaderStdin(stdinVO);
		stdinDAO.insertDetailStdin(stdinVO);
	}
}
