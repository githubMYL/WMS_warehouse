package org.warehouse.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.warehouse.configs.models.mapper.WactrDAO;
import org.warehouse.models.user.UserVO;
import org.warehouse.models.wactr.WactrForm;
import org.warehouse.models.wactr.WactrVO;

@Service
@RequiredArgsConstructor
public class WactrRegisterService {
	private final WactrDAO wactrDAO;

	public void register(WactrForm wactrForm) {
		WactrVO wactrVO = new ModelMapper().map(wactrForm, WactrVO.class);

		if(wactrVO.getTel().contains("-") || wactrVO.getTel().contains(" ")) {
			wactrVO.setTel(wactrVO.getTel().replace("-", ""));
			wactrVO.setTel(wactrVO.getTel().replace(" ", ""));
		}
		wactrVO.setRemark(wactrForm.getRemk());

		System.out.println(wactrVO);

		wactrDAO.insertWactr(wactrVO);
	}
}
