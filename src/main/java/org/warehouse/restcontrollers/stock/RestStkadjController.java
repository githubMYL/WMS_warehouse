package org.warehouse.restcontrollers.stock;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.warehouse.configs.models.mapper.StockDAO;
import org.warehouse.models.stock.StkadjModService;
import org.warehouse.models.stock.StkadjVO;

import java.util.HashMap;
import java.util.List;

@RestController("StkAdjRestController")
@RequestMapping("/stkadj/ajaxstkadj")
@RequiredArgsConstructor
public class RestStkadjController {

	private final StockDAO stockDAO;
	private final StkadjModService modService;



	@GetMapping("getDetail")
	public List<StkadjVO> getDetail(String[] tmstkCd) {

		HashMap<String, String> tmstkCdMap = new HashMap<>();

		tmstkCdMap.put("clnt_cd", tmstkCd[0]);
		tmstkCdMap.put("item_cd", tmstkCd[1]);
		tmstkCdMap.put("wactr_cd", tmstkCd[2]);
		tmstkCdMap.put("loc_cd", tmstkCd[3]);

		List<StkadjVO> stkadjList = stockDAO.getDetail(tmstkCdMap);

		return stkadjList;
	}

	@GetMapping("getSearch")
	public List<StkadjVO> getSearch(String searchData){

		String[] search = searchData.split(",");

		HashMap<String,String> searchArray = new HashMap<>();

		searchArray.put("clnt_cd", search[0]);
		searchArray.put("item_cd", search[1]);
		searchArray.put("wactr_cd", search[2]);
		searchArray.put("loc_cd", search[3]);
		searchArray.put("mod_dt_start", search[4]);
		searchArray.put("mod_dt_end", search[5]);


		List<StkadjVO> stkadjSearchList = stockDAO.getSearch(searchArray);

		return stkadjSearchList;

	};
}