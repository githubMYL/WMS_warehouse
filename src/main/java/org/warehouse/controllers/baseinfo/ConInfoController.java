package org.warehouse.controllers.baseinfo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.warehouse.configs.models.mapper.ClntDAO;
import org.warehouse.configs.models.mapper.ConInfoDAO;
import org.warehouse.models.admin.clnt.ClntVO;
import org.warehouse.models.baseinfo.coninf.ConInfoService;
import org.warehouse.models.baseinfo.coninf.ConInfoVO;
import org.warehouse.models.baseinfo.coninf.ConInfoValidator;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/baseinfo")
public class ConInfoController {

	private final ConInfoService conInfoService;

	private final ConInfoValidator validator;

	private final ConInfoDAO conInfoDAO;

	private final ClntDAO clntDAO;

	@GetMapping("/coninfo")
	public String conInfo(@ModelAttribute("srchParams") ConInfoVO srchParam, Model model) {
		System.out.println("#################################################");

		commonProcess(model);
		List<ConInfoVO> conInfoList = new ArrayList<>();
		System.out.println("srchParam ::: " + srchParam);

		if (srchParam.getClntNm() != null) {
			conInfoList = conInfoDAO.getConListSearch(srchParam);
		}

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
//        System.out.println("Controller :: mMin :: " + conInfoVO.getMMin());
//        System.out.println("Controller :: pltFee :: " + conInfoVO.getPltFee());
//        System.out.println("Controller :: transSdt :: " + conInfoVO.getTransSdt());
//        System.out.println("Controller :: transEdt :: " + conInfoVO.getTransEdt());

		conInfoVO.setRegNm("session");

		conInfoService.conInfoSave(conInfoVO);
		return "redirect:/baseinfo/coninfo";

	}
	private void commonProcess(Model model) {
		String Title = "기본정보::계약정보";
		String menuCode = "conInfo";
		String pageName = "baseinfo";
		model.addAttribute("pageName", pageName);
		model.addAttribute("Title", Title);
		model.addAttribute("menuCode", menuCode);
	}
}