package org.warehouse.controllers.admins;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.warehouse.configs.models.mapper.*;
import org.warehouse.models.clnt.ClntVO;
import org.warehouse.models.cust.CustCustCtrVO;
import org.warehouse.models.cust.CustVO;
import org.warehouse.models.custctr.CustCtrVO;
import org.warehouse.models.user.UserJoinValidator;
import org.warehouse.services.UserJoinService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
	private final UserJoinService joinService;
	private final UserJoinValidator joinValidator;

	private final ClntDAO clntDAO;
	private final CustDAO custDAO;
	private final TestDAO testDAO;

	@GetMapping("/join")
	public String join(Model model) {
		JoinForm joinForm = new JoinForm();
		List<ClntVO> clntList = clntDAO.getClntList();
		List<CustVO> custList = custDAO.getCustList();

		//List<CustCustCtrVO> custCustCtrList = testDAO.getCustCustCtrList();

		model.addAttribute("joinForm", joinForm);
		model.addAttribute("clntList", clntList);
		model.addAttribute("custList", custList);
		//model.addAttribute("custCustCtrList", custCustCtrList);

		return "admin/join";
	}

	@PostMapping("/join")
	public String joinPs(@Valid JoinForm joinForm, Errors errors, Model model) {
		joinValidator.validate(joinForm, errors);

		if(errors.hasErrors()) {
			return "admin/join";
		}

		joinService.join(joinForm);

		return "redirect:/user/login";
	}
}
