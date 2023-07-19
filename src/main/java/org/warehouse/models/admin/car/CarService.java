package org.warehouse.models.admin.car;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.warehouse.configs.models.mapper.CarDAO;
import org.warehouse.controllers.users.UserInfo;
import org.warehouse.models.admin.clnt.ClntForm;
import org.warehouse.models.admin.clnt.ClntVO;

@Service
@RequiredArgsConstructor
public class CarService {
	private final HttpSession session;
	private final CarDAO carDAO;

	public void register(CarForm carForm) {
		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");

		CarVO carVO = new ModelMapper().map(carForm, CarVO.class);

		if(carForm.getFlag() == null) {
			carVO.setRegNm(userInfo.getUserNm());
			carDAO.insertCar(carVO);
		} else {
			carVO.setModNm(userInfo.getUserNm());
			carDAO.updateCar(carVO);
		}
	}
}
