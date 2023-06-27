package org.warehouse.controllers.baseinfo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.warehouse.configs.models.mapper.ClntDAO;
import org.warehouse.configs.models.mapper.ItemInfoDAO;
import org.warehouse.models.admin.clnt.ClntVO;
import org.warehouse.models.baseinfo.iteminfo.ItemInfoService;
import org.warehouse.models.baseinfo.iteminfo.ItemInfoVO;


import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/baseinfo")
public class ItemInfoController {

	private final ItemInfoDAO itemInfoDAO;
	private final ClntDAO clntDAO;
	private final ItemInfoService itemInfoService;

	@GetMapping("/iteminfo")
	public String iteminfo(Model model) {

		ItemInfoVO itemInfoVO = new ItemInfoVO();

		/** 물류센터 코드 S */
		//List<WactrVO> wactrList = wactrDAO.getGetList;
		/** 물류센터 코드 E */

		/** 고객사 코드 S */
		List<ClntVO> clntList = clntDAO.getClntList();
		model.addAttribute("clntList", clntList);
		/** 고객사 코드 E */

		/** 관리단위 S */
		List<ItemInfoVO> codeList = itemInfoDAO.getCodeList();
		model.addAttribute("codeList", codeList);
		/** 관리단위 E */

		model.addAttribute("itemInfoVO", itemInfoVO);

		return "baseinfo/iteminfo";
	}

	@PostMapping("/iteminfo")
	public String iteminfoPs(@Valid ItemInfoVO itemInfoVO, Errors errors, Model model) {

		System.out.println("Controller :: " + itemInfoVO);

		if(errors.hasErrors()) {
			return "baseinfo/iteminfo";
		}

		itemInfoVO.setRegNm("session");
		itemInfoService.itemInfoSave(itemInfoVO);

		return "redirect:/baseinfo/iteminfo";
	}

}