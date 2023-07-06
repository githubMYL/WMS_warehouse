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
import org.warehouse.models.baseinfo.coninfo.ConInfoService;
import org.warehouse.models.baseinfo.coninfo.ConInfoVO;
import org.warehouse.models.baseinfo.coninfo.ConInfoValidator;

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
}