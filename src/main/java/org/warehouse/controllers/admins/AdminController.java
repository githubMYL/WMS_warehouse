package org.warehouse.controllers.admins;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.warehouse.configs.models.mapper.*;
import org.warehouse.controllers.users.UserInfo;
import org.warehouse.models.admin.clnt.ClntForm;
import org.warehouse.models.admin.clnt.ClntService;
import org.warehouse.models.admin.clnt.ClntVO;
import org.warehouse.models.admin.clnt.ClntValidator;
import org.warehouse.models.admin.cust.CustForm;
import org.warehouse.models.admin.cust.CustService;
import org.warehouse.models.admin.cust.CustVO;
import org.warehouse.models.admin.custctr.CustCtrVO;
import org.warehouse.models.stdin.StdinForm;
import org.warehouse.models.stdin.StdinVO;
import org.warehouse.models.user.UserJoinService;
import org.warehouse.models.user.UserJoinValidator;
import org.warehouse.models.user.UserVO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
	private final UserJoinService joinService;
	private final UserJoinValidator joinValidator;
	private final ClntService clntService;
	private final ClntValidator clntValidator;
	private final CustService custService;

	private final UserDAO userDAO;
	private final ClntDAO clntDAO;
	private final CustDAO custDAO;
	private final CustCtrDAO custCtrDAO;

	private final HttpServletResponse response;



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
		List<CustCtrVO> custCtrList = custCtrDAO.getCustCtrList();

		model.addAttribute("joinForm", joinForm);
		model.addAttribute("clntList", clntList);
		model.addAttribute("custList", custCtrList);

		return "admin/join";
	}

	@PostMapping("/join")
	public String joinPs(@Valid JoinForm joinForm, Errors errors, Model model) {
		joinValidator.validate(joinForm, errors);

		if(errors.hasErrors()) {
			List<ClntVO> clntList = clntDAO.getClntList();
			List<CustCtrVO> custCtrList = custCtrDAO.getCustCtrList();

			model.addAttribute("clntList", clntList);
			model.addAttribute("custList", custCtrList);
			return "admin/join";
		}

		joinService.join(joinForm);

		closeLayer(response);

		return "close";
	}
	/** userManage E */



	/** clntManage S */
	@GetMapping("/clntManage")
	public String clntManage(Model model) {
		commonProcess(model, "clntManage");
		List<ClntVO> clntList = clntDAO.getClntList();

		model.addAttribute("clntList", clntList);
		return "admin/clnt/clntManage";
	}

	@GetMapping("/clntManage/register")
	public String clntRegister(Model model){
		ClntForm clntForm = new ClntForm();

		model.addAttribute("clntForm", clntForm);

		return "admin/clnt/clntRegister";
	}

	@GetMapping("/clntManage/update/{clntCd}")
	public String clntUpdate(@PathVariable String clntCd, Model model) {
		ClntVO clntVO = clntDAO.getClntByCd(clntCd);

		ClntForm clntForm = new ModelMapper().map(clntVO, ClntForm.class);

		model.addAttribute("clntForm", clntForm);

		return("admin/clnt/clntUpdate");
	}


	@PostMapping("/clntManage/save")
	public String clntSave(@Valid ClntForm clntForm, Errors errors, Model model){
		clntValidator.validate(clntForm, errors);
		if(errors.hasErrors()) {
			return "admin/clnt/clntRegister";
		}

		clntService.register(clntForm);

		closeLayer(response);
		
		return "close";
	}
	/** clntManage E */



	/** custManage S */
	@GetMapping("/custManage")
	public String custManage(Model model) {
		commonProcess(model, "custManage");
		List<CustVO> custList = custDAO.getCustList();

		model.addAttribute("custList", custList);
		return "admin/cust/custManage";
	}

	@GetMapping("/custManage/register")
	public String custRegister(Model model) {
		CustForm custForm = new CustForm();

		model.addAttribute("custForm", custForm);

		return "admin/cust/custRegister";
	}

	@GetMapping("/custManage/update/{custCd}")
	public String custUpdate(@PathVariable String custCd, Model model) {
		CustVO custVO = custDAO.getCustByCd(custCd);

		CustForm custForm = new ModelMapper().map(custVO, CustForm.class);

		model.addAttribute("custForm", custForm);

		return("admin/cust/custUpdate");
	}

	@PostMapping("/custManage/save")
	public String custSave(@Valid CustForm custForm, Errors errors, Model model) {
		//validator
		if(errors.hasErrors()) {
			return "admin/cust/custRegister";
		}

		custService.register(custForm);

		closeLayer(response);

		return "close";
	}


	/** custManage E */

	/** custCtrManage S */
	@GetMapping("/custCtrManage")
	public String custCtrManage(Model model) {
		commonProcess(model, "custCtrManage");
		List<CustCtrVO> custCtrList = custCtrDAO.getCustCtrList();
		System.out.println(custCtrList);

		model.addAttribute("custCtrList", custCtrList);
		return "admin/custctr/custCtrManage";
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
