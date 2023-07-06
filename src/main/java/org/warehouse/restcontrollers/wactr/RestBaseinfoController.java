package org.warehouse.restcontrollers.wactr;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.warehouse.configs.models.mapper.WactrDAO;
import org.warehouse.models.baseinfo.wactr.WactrVO;

import java.util.List;

@RestController("baseinfoAjaxController")
@RequestMapping("/baseinfo")
@RequiredArgsConstructor
public class RestBaseinfoController {
	private final WactrDAO wactrDAO;

	@GetMapping("wactr/getSearch")
	public List<WactrVO> getWactrSearch(String wactrNm, String wactrScale){
		System.out.println(wactrNm);
		System.out.println(wactrScale);

		List<WactrVO> list = wactrDAO.getListByNmScale(wactrNm, wactrScale);

		return list;
	}

	@GetMapping("wactr/delete")
	public void delete(String wactrCd) {
		String[] wactrCdList = wactrCd.split(",");
		for(int i = 0; i < wactrCdList.length; i++) {
			wactrDAO.deleteWactr(wactrCdList[i]);
		}
	}
}
