package org.warehouse.configs.models.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.warehouse.models.admin.car.CarVO;
import org.warehouse.models.admin.clnt.ClntVO;

import java.util.List;

@Mapper
public interface CarDAO {
	List<CarVO> getCarList();
	void insertCar(CarVO carVO);
	void updateCar(CarVO carVO);
	void deleteCar(String carCd);
}
