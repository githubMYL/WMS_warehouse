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
import org.warehouse.models.admin.clnt.ClntVO;
import org.warehouse.models.admin.cust.CustVO;
import org.warehouse.models.admin.custctr.CustCtrVO;
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

	private final UserDAO userDAO;
	private final ClntDAO clntDAO;
	private final CustDAO custDAO;
	private final CustCtrDAO custCtrDAO;

	/** userManage S */
	@GetMapping("/userManage")
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
			List<ClntVO> clntList = clntDAO.getClntList();
			List<CustVO> custList = custDAO.getCustList();

			model.addAttribute("clntList", clntList);
			model.addAttribute("custList", custList);
			return "admin/join";
		}

		joinService.join(joinForm);

		return "redirect:/admin/userManage";
	}
	/** userManage E */

	/** clntManage S */
	@GetMapping("/clntManage")
	public String clntManage(Model model) {
		commonProcess(model, "clntManage");
		List<ClntVO> clntList = clntDAO.getClntList();

		model.addAttribute("clntList", clntList);
		return "admin/clntManage";
	}
	/** clntManage E */

	/** custManage S */
	@GetMapping("/custManage")
	public String custManage(Model model) {
		commonProcess(model, "custManage");
		List<CustVO> custList = custDAO.getCustList();

		model.addAttribute("custList", custList);
		return "admin/custManage";
	}
	/** custManage E */

	/** custCtrManage S */
	@GetMapping("/custCtrManage")
	public String custCtrManage(Model model) {
		commonProcess(model, "custCtrManage");
		List<CustCtrVO> custCtrList = custCtrDAO.getCustCtrList();
		System.out.println(custCtrList);

		model.addAttribute("custCtrList", custCtrList);
		return "admin/custCtrManage";
	}
	/** custCtrManage E */

	/** carManage S */
	@GetMapping("/carManage")
	public String carManage(Model model) {
		return null;
	}
	/** carManage E */

	/** commonMethod S */
	private void commonProcess(Model model) {
		commonProcess(model, "userManage");
	}

	private void commonProcess(Model model, String menuCode) {
		String Title = "관리자::사용자관리";
		String pageName = "admin";

		model.addAttribute("menuCode", menuCode);
		model.addAttribute("pageName", pageName);
		model.addAttribute("Title", Title);
		model.addAttribute("menuCode", menuCode);
	}
	/** commonMethod E*/
}
