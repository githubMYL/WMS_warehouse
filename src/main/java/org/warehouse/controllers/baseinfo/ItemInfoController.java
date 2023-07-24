package org.warehouse.controllers.baseinfo;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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


import java.io.IOException;
import java.io.PrintWriter;

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
	private final HttpServletResponse response;

	/** 상품정보 리스트 */
	@GetMapping("/iteminfo")
	public String iteminfo(@ModelAttribute("srchParams") ItemInfoVO srchParams, Model model){

		commonProcess(model);

		if(srchParams.getClntNm() == null && srchParams.getItemCd() == null && srchParams.getItemNm() == null){
			srchParams.setClntNm("");
			srchParams.setItemCd("");
			srchParams.setItemNm("");
		}

		List<ItemInfoVO> itemInfoList = itemInfoDAO.getItemListSrch(srchParams);

		model.addAttribute("itemInfoList", itemInfoList);

		return "baseinfo/iteminfo";
	}

	/** 상품정보 수정 팝업 */
	@GetMapping("/iteminfo/{keyVal}/update")
	public String update(@PathVariable String keyVal, Model model) {
		commonProcess(model);

		/** 물류센터 코드 S */
		List<WactrVO> wactrList = wactrDAO.getList();
		model.addAttribute("wactrList", wactrList);
		/** 물류센터 코드 E */

		/** 고객사 코드 S */
		List<ClntVO> clntList = clntDAO.getClntList();
		model.addAttribute("clntList", clntList);
		/** 고객사 코드 E */

		/** 관리단위 S */
		List<ItemInfoVO> codeList = itemInfoDAO.getCodeList();
		model.addAttribute("codeList", codeList);
		/** 관리단위 E */

		ItemInfoVO itemInfoVO = itemInfoDAO.getKeyCd(keyVal);

		if(itemInfoVO.getPltInBox() == null)
			itemInfoVO.setPltInBox(0L);

		itemInfoVO.setUpdYn("Y");
		itemInfoVO.setKeyVal(keyVal);
		model.addAttribute("itemInfoVO", itemInfoVO);

		return "baseinfo/popup/itemInfoPop";
	}

	/** 상품정보 추가 팝업 */
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

		/** 관리단위 S */
		List<ItemInfoVO> codeList = itemInfoDAO.getCodeList();
		model.addAttribute("codeList", codeList);
		/** 관리단위 E */

		if(itemInfoVO.getPltInBox() == null)
			itemInfoVO.setPltInBox(0L);

		model.addAttribute("itemInfoVO", itemInfoVO);

		return "baseinfo/popup/itemInfoPop";
	}

	/** 상품정보 저장 & 수정 처리 */
	@PostMapping("/iteminfo")
	public String iteminfoPs(@Valid ItemInfoVO itemInfoVO, Errors errors, Model model) {

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

			/** 관리단위 S */
			List<ItemInfoVO> codeList = itemInfoDAO.getCodeList();
			model.addAttribute("codeList", codeList);
			/** 관리단위 E */

			return "baseinfo/popup/itemInfoPop";
		}

		if(itemInfoVO.getUpdYn() == null){
			System.out.println("===== 추가 진입 =====");
			itemInfoVO.setRegNm("session");
			itemInfoService.itemInfoSave(itemInfoVO);
		}
		else if(itemInfoVO.getUpdYn().equals("Y")){
			System.out.println("===== 수정 진입 =====");
			itemInfoVO.setModNm("session");
			itemInfoService.itemInfoUpdate(itemInfoVO);
		}

		closeLayer(response);

		return "redirect:/baseinfo/iteminfo";
	}

	/** 상품정보 삭제 처리 (DEL_YN UPDATE) */
	@GetMapping("iteminfo/deleteItem")
	public String deleteItem(String chkArr) {

		// 문자열 구분자 ,
		String[] chkData = chkArr.split(",");

		// DEL_YN = 'Y' UPDATE
		for(int i = 0; i < chkData.length; i++){
			itemInfoDAO.deleteItem(chkData[i]);
			System.out.println("체크데이터 : " + chkData[i]);
		}
		return "redirect:/baseinfo/iteminfo";
	}

	private void commonProcess(Model model) {
		String Title = "기본정보::상품정보";
		String menuCode = "iteminfo";
		String pageName = "baseinfo";
		model.addAttribute("Title", Title);
		model.addAttribute("menuCode", menuCode);
		model.addAttribute("pageName", pageName);
	}

	private void closeLayer(HttpServletResponse response) {
		response.setContentType("text/html; charset=euc-kr");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.println("<script>var parent = window.parent.document;" +
					"var layerDim = parent.getElementById('layer_dim');" +
					"var layerPopup = parent.getElementById('layer_popup');" +
					"parent.body.removeChild(layerDim);" +
					"parent.body.removeChild(layerPopup);" +
					"parent.location.reload();</script>");
			out.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}