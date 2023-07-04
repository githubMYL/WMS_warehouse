package org.warehouse.controllers.admins;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.warehouse.configs.models.mapper.ClntDAO;
import org.warehouse.configs.models.mapper.CustDAO;
import org.warehouse.configs.models.mapper.TestDAO;
import org.warehouse.configs.models.mapper.UserDAO;
import org.warehouse.models.admin.clnt.ClntVO;
import org.warehouse.models.admin.cust.CustVO;
import org.warehouse.models.user.UserJoinService;
import org.warehouse.models.user.UserJoinValidator;
import org.warehouse.models.user.UserVO;

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
	private final UserDAO userDAO;

	@GetMapping
	public String userManage(Model model) {
		commonProcess(model);
		List<UserVO> userList = userDAO.getUserList();

		model.addAttribute("userList", userList);

		return "admin/userManage";
	}

	@GetMapping("/join")
	public String join(Model model) {
		JoinForm joinForm = new JoinForm();
		List<ClntVO> clntList = clntDAO.getClntList();
		List<CustVO> custList = custDAO.getCustList();

		model.addAttribute("joinForm", joinForm);
		model.addAttribute("clntList", clntList);
		model.addAttribute("custList", custList);

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

	private void commonProcess(Model model) {
		String Title = "관리자::사용자관리";
		String menuCode = "admin";
		String pageName = "userManage";
		model.addAttribute("pageName", pageName);
		model.addAttribute("Title", Title);
		model.addAttribute("menuCode", menuCode);
	}
}
