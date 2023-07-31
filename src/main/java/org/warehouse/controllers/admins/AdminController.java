package org.warehouse.controllers.admins;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.warehouse.configs.models.mapper.*;
import org.warehouse.controllers.users.UserInfo;
import org.warehouse.models.admin.car.CarForm;
import org.warehouse.models.admin.car.CarService;
import org.warehouse.models.admin.car.CarVO;
import org.warehouse.models.admin.clnt.ClntForm;
import org.warehouse.models.admin.clnt.ClntService;
import org.warehouse.models.admin.clnt.ClntVO;
import org.warehouse.models.admin.clnt.ClntValidator;
import org.warehouse.models.admin.cust.CustForm;
import org.warehouse.models.admin.cust.CustService;
import org.warehouse.models.admin.cust.CustVO;
import org.warehouse.models.admin.custctr.CustCtrForm;
import org.warehouse.models.admin.custctr.CustCtrService;
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
	private final CustCtrService custCtrService;
	private final CarService carService;

	private final UserDAO userDAO;
	private final ClntDAO clntDAO;
	private final CustDAO custDAO;
	private final CustCtrDAO custCtrDAO;
	private final CarDAO carDAO;

	private final HttpServletResponse response;



	/** userManage S */
	@GetMapping("/userManage")
	public String userManage(Model model) {
		commonProcess(model, "userManage", "사용자관리");
		List<UserVO> userList = userDAO.getUserList();

		model.addAttribute("userList", userList);

		return "admin/user/userManage";
	}

	@GetMapping("/userManage/join")
	public String join(Model model) {
		JoinForm joinForm = new JoinForm();
		List<ClntVO> clntList = clntDAO.getClntList();
		List<CustCtrVO> custCtrList = custCtrDAO.getCustCtrList();

		model.addAttribute("joinForm", joinForm);
		model.addAttribute("clntList", clntList);
		model.addAttribute("custList", custCtrList);

		return "admin/user/join";
	}

	@GetMapping("/userManage/update/{userId}")
	public String update(@PathVariable String userId, Model model) {
		UserVO userVO = userDAO.getUserById(userId);
		System.out.println(userId);
		System.out.println(userVO);
		JoinForm joinForm = new ModelMapper().map(userVO, JoinForm.class);

		List<ClntVO> clntList = clntDAO.getClntList();
		List<CustCtrVO> custCtrList = custCtrDAO.getCustCtrList();


		model.addAttribute("joinForm", joinForm);
		model.addAttribute("clntList", clntList);
		model.addAttribute("custList", custCtrList);

		return "admin/user/update";
	}

	@PostMapping("/userManage/join")
	public String joinPs(@Valid JoinForm joinForm, Errors errors, Model model) {
		System.out.println(joinForm);
		joinValidator.validate(joinForm, errors);

		if(errors.hasErrors()) {
			List<ClntVO> clntList = clntDAO.getClntList();
			List<CustCtrVO> custCtrList = custCtrDAO.getCustCtrList();

			model.addAttribute("clntList", clntList);
			model.addAttribute("custList", custCtrList);
			return "admin/user/join";
		}

		joinService.join(joinForm);

		closeLayer(response);

		return "close";
	}
	/** userManage E */



	/** clntManage S */
	@GetMapping("/clntManage")
	public String clntManage(Model model) {
		commonProcess(model, "clntManage", "고객사관리");
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
		commonProcess(model, "custManage", "납품처관리");
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
		commonProcess(model, "custCtrManage", "납품센터관리");
		List<CustCtrVO> custCtrList = custCtrDAO.getCustCtrList();
		System.out.println(custCtrList);

		model.addAttribute("custCtrList", custCtrList);
		return "admin/custctr/custCtrManage";
	}

	@GetMapping("/custCtrManage/register")
	public String custCtrRegister(Model model) {
		List<CustVO> custList = custDAO.getCustList();
		CustCtrForm custCtrForm = new CustCtrForm();

		model.addAttribute("custList", custList);
		model.addAttribute("custCtrForm", custCtrForm);

		return "admin/custctr/custCtrRegister";
	}

	@GetMapping("/custCtrManage/update")
	public String custCtrUpdate(@RequestParam(value="custCd") String custCd, @RequestParam(value="custCtrCd")String custCtrCd, Model model) {
		CustCtrVO custCtrVO = custCtrDAO.getCustCtrByCd(custCd, custCtrCd);
		System.out.println(custCtrVO);

		CustCtrForm custCtrForm = new ModelMapper().map(custCtrVO, CustCtrForm.class);

		model.addAttribute("custCtrForm", custCtrForm);

		return("admin/custctr/custCtrUpdate");
	}


	@PostMapping("/custCtrManage/save")
	public String custCtrSave(@Valid CustCtrForm custCtrForm, Errors errors, Model model) {
		//validator
		if(errors.hasErrors()) {
			return "admin/custCtr/custCtrRegister";
		}

		custCtrService.register(custCtrForm);

		closeLayer(response);

		return "close";
	}

	/** custCtrManage E */

	/** carManage S */
	@GetMapping("/carManage")
	public String carManage(@Param("search_carNum")String search_carNum, @Param("search_driverNm") String search_driverNm, Model model) {
		commonProcess(model, "carManage", "차량관리");

		if(search_carNum != null || search_driverNm != null ) {
			List<CarVO> searchList = carDAO.getCarSearch(search_carNum, search_driverNm);
			model.addAttribute("carList", searchList);

		} else {
			List<CarVO> carList = carDAO.getCarList();
			model.addAttribute("carList", carList);
		}

		return "admin/car/carManage";
	}

	@GetMapping("/carManage/register")
	public String carRegister(Model model) {
		CarForm carForm = new CarForm();

		model.addAttribute("carForm", carForm);

		return "admin/car/carRegister";
	}

	@GetMapping("/carManage/update/{carCd}")
	public String carUpdate(@PathVariable String carCd, Model model) {
		CarVO carVO = carDAO.getCar(carCd);
		CarForm carForm = new ModelMapper().map(carVO, CarForm.class);

		model.addAttribute("carForm", carForm);
		return "admin/car/carUpdate";
	}


	@PostMapping("/carManage/save")
	public String carSave(@Valid CarForm carForm, Errors errors) {
		if(errors.hasErrors()) {
			return "admin/car/carRegister";
		}

		carService.register(carForm);

		closeLayer(response);

		return "close";
	}


	/** carManage E */

	/** commonMethod S */
	private void commonProcess(Model model) {
		commonProcess(model, "userManage", null);
	}

	private void commonProcess(Model model, String menuCode, String Title) {
		String title = "관리자::"+Title;
		String pageName = "admin";

		model.addAttribute("menuCode", menuCode);
		model.addAttribute("pageName", pageName);
		model.addAttribute("Title", title);
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
