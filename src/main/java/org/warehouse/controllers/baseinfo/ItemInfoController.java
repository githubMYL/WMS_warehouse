package org.warehouse.controllers.baseinfo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.warehouse.configs.models.mapper.ClntDAO;
import org.warehouse.configs.models.mapper.ItemInfoDAO;
import org.warehouse.configs.models.mapper.LocDAO;
import org.warehouse.configs.models.mapper.WactrDAO;
import org.warehouse.models.admin.clnt.ClntVO;
import org.warehouse.models.baseinfo.iteminfo.ItemInfoService;
import org.warehouse.models.baseinfo.iteminfo.ItemInfoVO;
import org.warehouse.models.baseinfo.iteminfo.ItemInfoValidator;
import org.warehouse.models.baseinfo.loc.LocVO;
import org.warehouse.models.baseinfo.wactr.WactrVO;


import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/baseinfo")
public class ItemInfoController{

	private final WactrDAO wactrDAO;
	private final ClntDAO clntDAO;
	private final LocDAO locDAO;
	private final ItemInfoDAO itemInfoDAO;
	private final ItemInfoService itemInfoService;
	private final ItemInfoValidator itemInfoValidator;

	@GetMapping("/iteminfo")
	public String iteminfo(@ModelAttribute("srchParams") ItemInfoVO srchParams, Model model){

		commonProcess(model);

		System.out.println("sechParams ::: " + srchParams);
		if(srchParams.getClntNm() == null && srchParams.getItemCd() == null && srchParams.getItemNm() == null){
			srchParams.setClntNm("");
			srchParams.setItemCd("");
			srchParams.setItemNm("");
		}

		List<ItemInfoVO> itemInfoList = itemInfoDAO.getItemListSrch(srchParams);

		model.addAttribute("itemInfoList", itemInfoList);

		return "baseinfo/iteminfo";
	}

	@GetMapping("/iteminfo/register")
	public String register(Model model) {

		ItemInfoVO itemInfoVO = new ItemInfoVO();

		/** 물류센터 코드 S */
		List<WactrVO> wactrList = wactrDAO.getList();
		model.addAttribute("wactrList", wactrList);
		/** 물류센터 코드 E */

		/** 고객사 코드 S */
		List<ClntVO> clntList = clntDAO.getClntList();
		model.addAttribute("clntList", clntList);
		/** 고객사 코드 E */

		/** 로케이션 코드 S */
		List<LocVO> locList = locDAO.getLocList();
		model.addAttribute("locList", locList);
		/** 로케이션 코드 E */

		/** 관리단위 S */
		List<ItemInfoVO> codeList = itemInfoDAO.getCodeList();
		model.addAttribute("codeList", codeList);
		/** 관리단위 E */

		if(itemInfoVO.getPltInBox() == null)
			itemInfoVO.setPltInBox(0L);

		model.addAttribute("itemInfoVO", itemInfoVO);

		return "baseinfo/popup/itemInfoPop";
	}

	@PostMapping("/iteminfo")
	public String iteminfoPs(@Valid ItemInfoVO itemInfoVO, Errors errors, Model model) {

		System.out.println("Controller :: " + itemInfoVO);

		itemInfoValidator.validate(itemInfoVO, errors);

		if(errors.hasErrors()) {

			/** 물류센터 코드 S */
			List<WactrVO> wactrList = wactrDAO.getList();
			model.addAttribute("wactrList", wactrList);
			/** 물류센터 코드 E */

			/** 고객사 코드 S */
			List<ClntVO> clntList = clntDAO.getClntList();
			model.addAttribute("clntList", clntList);
			/** 고객사 코드 E */

			/** 로케이션 코드 S */
			List<LocVO> locList = locDAO.getLocList();
			model.addAttribute("locList", locList);
			/** 로케이션 코드 E */

			/** 관리단위 S */
			List<ItemInfoVO> codeList = itemInfoDAO.getCodeList();
			model.addAttribute("codeList", codeList);
			/** 관리단위 E */

			return "baseinfo/popup/itemInfoPop";
		}

		itemInfoVO.setRegNm("session");
		itemInfoService.itemInfoSave(itemInfoVO);

		return "redirect:/baseinfo/iteminfo";
	}

	@GetMapping("/iteminfo/deleteItem")
	public String deleteItem(String idx) {

		System.out.println("idx :: " + idx);
		return "redirect:/baseinfo/iteminfo";
	}
//	@ResponseBody
//	@RequestMapping(value="/folderDelete.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//	public int folderDelete(HttpServletRequest request, @RequestParam(value="checkBoxArr[]") List<String> checkBoxArr, @ModelAttribute("archiveFolder") ArchiveFolder archiveFolder) throws Exception {
//		int result = 0;
//		String checkNum = "";
//
//		for(String str : checkBoxArr){
//			checkNum = str;
//			archiveFolder.setFolderSeq(checkNum);
//			folderService.archiveFolderDelete(archiveFolder);
//		}
//		return result;
//	}



	private void commonProcess(Model model) {
		String Title = "기본정보::상품정보";
		String menuCode = "iteminfo";
		String pageName = "baseinfo";
		model.addAttribute("pageName", pageName);
		model.addAttribute("Title", Title);
		model.addAttribute("menuCode", menuCode);
	}
}