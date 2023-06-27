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
import org.warehouse.configs.models.mapper.ConInfoDAO;
import org.warehouse.models.admin.clnt.ClntVO;
import org.warehouse.models.baseinfo.coninf.ConInfoService;
import org.warehouse.models.baseinfo.coninf.ConInfoVO;
import org.warehouse.models.baseinfo.coninf.ConInfoValidator;

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
	public String conInfo(Model model) {

		ConInfoVO conInfoVO = new ConInfoVO();
		//List<ConInfoVO> conInfoVOList = conInfoDAO.getConInfoList();
		/** 고객사 코드 S */
		List<ClntVO> clntList = clntDAO.getClntList();
		model.addAttribute("clntList", clntList);
		/** 고객사 코드 E */

		model.addAttribute("conInfoVO", conInfoVO);

		return "/baseinfo/coninfo";

	}

	@PostMapping("/coninfo")
	public String conInfoPs(@Valid ConInfoVO conInfoVO, Errors errors, Model model) {

		System.out.println("여기서 시작 :: " + conInfoVO);

		validator.validate(conInfoVO, errors);
		System.out.println("여기서 멈춤 :: " + conInfoVO);

		if(errors.hasErrors()) {
			return "/baseinfo/coninfo";
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