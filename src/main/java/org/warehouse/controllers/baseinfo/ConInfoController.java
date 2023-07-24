package org.warehouse.controllers.baseinfo;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.warehouse.configs.models.mapper.ClntDAO;
import org.warehouse.configs.models.mapper.ConInfoDAO;
import org.warehouse.models.admin.clnt.ClntVO;
import org.warehouse.models.baseinfo.coninfo.ConInfoService;
import org.warehouse.models.baseinfo.coninfo.ConInfoVO;
import org.warehouse.models.baseinfo.coninfo.ConInfoValidator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/baseinfo")
public class ConInfoController {

	private final ConInfoService conInfoService;

	private final ConInfoValidator validator;

	private final ConInfoDAO conInfoDAO;

	private final ClntDAO clntDAO;

	private final HttpServletResponse response;

	@GetMapping("/coninfo")
	public String conInfo(@ModelAttribute("srchParams") ConInfoVO srchParam, Model model) {
		System.out.println("#################################################");

		commonProcess(model);
		if (srchParam.getClntNm() == null)
			srchParam.setClntNm("");
		System.out.println("srchParam ::: " + srchParam);
		List<ConInfoVO> conInfoList = conInfoDAO.getConListSearch(srchParam);

		model.addAttribute("conInfoList", conInfoList);

		return "baseinfo/conInfo";

	}

	@GetMapping("/coninfo/register")
	public String conInfoRegister(Model model) {

		ConInfoVO conInfoVO = new ConInfoVO();
		//List<ConInfoVO> conInfoVOList = conInfoDAO.getConInfoList();

		/** 고객사 명 S */
		List<ClntVO> clntList = clntDAO.getClntList();
		model.addAttribute("clntList", clntList);
		/** 고객사 명 E */

		model.addAttribute("conInfoVO", conInfoVO);

		return "/baseinfo/popup/conInfoPop";

	}

	// 수정 팝업 설정 시 값 유지
	@GetMapping("/coninfo/{keyVal}/update")
	public String conInfoUpdate(@PathVariable String keyVal, Model model) {

		System.out.println("controller keyVal : " + keyVal);
		/** 고객사 명 S */
		List<ClntVO> clntList = clntDAO.getClntList();
		model.addAttribute("clntList", clntList);
		/** 고객사 명 E */

		ConInfoVO conInfoVO = conInfoDAO.updateConInfo(keyVal);

		System.out.println("conInfoVO keyVal >>>>>>>>>>>>>>>>>>>>>>>>>>: " + conInfoVO);

		model.addAttribute("conInfoVO", conInfoVO);

		return "/baseinfo/popup/conInfoUpdatePop";

	}

	@PostMapping("/coninfo")
	public String conInfoPs(@Valid ConInfoVO conInfoVO, Errors errors, Model model) {

		validator.validate(conInfoVO, errors);

		if(errors.hasErrors()) {

			/** 고객사 명 S */
			List<ClntVO> clntList = clntDAO.getClntList();
			model.addAttribute("clntList", clntList);
			/** 고객사 명 E */

			return "/baseinfo/popup/conInfoPop";
		}
		System.out.println("Controller :: clntCd :: " + conInfoVO);
		System.out.println("Base  ::  remk :: " + conInfoVO.getRemark());

		conInfoService.conInfoSave(conInfoVO);
		closeLayer(response);

		return "redirect:/baseinfo/coninfo";

	}

	@GetMapping("coninfo/deleteCon")
	public String deleteCon(String chkArr) {

		System.out.println("chkArr : " + chkArr);
		// 문자열 구분자 ,
		String[] chkData = chkArr.split(",");
		System.out.println("chkData.length :: " + chkData.length);
		// DEL_YN = 'Y' UPDATE
		for(int i = 0; i < chkData.length; i++){
			System.out.println("chkData.length :: " + chkData.length);
			conInfoDAO.deleteConInfo(chkData[i]);
		}
		return "redirect:/baseinfo/coninfo";
	}
	public void commonProcess(Model model) {
		String Title = "기본정보::계약정보";
		String menuCode = "conInfo";
		String pageName = "baseinfo";
		model.addAttribute("pageName", pageName);
		model.addAttribute("Title", Title);
		model.addAttribute("menuCode", menuCode);
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