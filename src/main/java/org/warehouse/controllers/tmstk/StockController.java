package org.warehouse.controllers.tmstk;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.warehouse.configs.models.mapper.StockDAO;
import org.warehouse.models.stock.TmstkVO;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {

	private final StockDAO stockDAO;


	private void commonProcess(Model model) {
		String Title = "재고::시점재고";
		String menuCode = "tmstk";
		String pageName = "stock";
		model.addAttribute("pageName", pageName);
		model.addAttribute("Title", Title);
		model.addAttribute("menuCode", menuCode);
	}
	@GetMapping("/tmstk")
	public String tmstkList(Model model , String search_clntNm ,String search_itemCd,String search_itemNm){

		commonProcess(model);
		if (search_clntNm != null || search_itemCd!= null || search_itemNm!=null) {
			List<TmstkVO> search_list = stockDAO.getSearchList(search_clntNm,search_itemCd,search_itemNm);
			model.addAttribute("list", search_list);
		} else {
			List<TmstkVO> list = stockDAO.tmstkList();
			model.addAttribute("list", list);
		}


		return "stock/tmstk_list";
	}


	@GetMapping("/stkadj")
	public String stkadj(Model model){
		model.addAttribute("pageName", "stock");
		model.addAttribute("Title", "재고::재고조정");
		model.addAttribute("menuCode", "stkadj");





		return "stock/stkadj_list";
	}




	@GetMapping("/stktransf")
	public String stktransf(Model model){
		model.addAttribute("pageName", "stock");
		model.addAttribute("Title", "재고::재고이동");
		model.addAttribute("menuCode", "stktransf");

		List<TmstkVO> tmstkList = stockDAO.tmstkList();
		model.addAttribute("list", tmstkList);





		return "stock/stktransf_list";
	}



}