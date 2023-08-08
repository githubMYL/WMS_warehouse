package org.warehouse.controllers.tmstk;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.warehouse.configs.models.mapper.DlyCnpDAO;
import org.warehouse.models.stock.DlycnpVO;

import java.util.List;


/**
 * 0. Project : WMS 프로젝트
 *
 * 1. FileName : DlyCnpController.java
 * 2. Package : org.warehouse.controller.tmstk
 * 3. Comment : 일수불 컨트롤러클래스
 * 4. 작성자 : 임동영
 * 5. 작성일 : 2023-07-17
 * 6. 변경이력 :
 * 이름 : 일자 : 근거자료 : 변경내용
 * ------------------------------------------------------
 *  임동영 : 2023-07-17 : : 신규 개발.
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/stock")
public class DlyCnpController {

	private final DlyCnpDAO dlyCnpDAO;
	@GetMapping("/dlycnp")
	public String dlycnp(@ModelAttribute("srchParam") DlycnpVO srchParam, Model model){
		commonProcess(model);
		List<DlycnpVO> dlycnpList = dlyCnpDAO.getDlyCnpList(srchParam);

		model.addAttribute("dlycnpList", dlycnpList);

		return "stock/dlycnp";
	}

	private void commonProcess(Model model) {
		String Title = "재고::일수불";
		String menuCode = "dlycnp";
		String pageName = "stock";
		model.addAttribute("pageName", pageName);
		model.addAttribute("Title", Title);
		model.addAttribute("menuCode", menuCode);
	}

	@GetMapping("/dlycnptest")
	public String dlycnptest(Model model) {
		commonProcess(model);
		List<DlycnpVO> dlycnpList = dlyCnpDAO.getTest();
		System.out.println(dlycnpList);

		model.addAttribute("dlycnpList", dlycnpList);

		return "stock/dlycnp";
	}
}