package org.warehouse.controllers.stdin;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import org.warehouse.commons.MenuDetail;
import org.warehouse.commons.Menus;
import org.warehouse.configs.models.mapper.ClntDAO;
import org.warehouse.configs.models.mapper.ItemInfoDAO;
import org.warehouse.configs.models.mapper.LocDAO;
import org.warehouse.configs.models.mapper.StdinDAO;
import org.warehouse.models.admin.clnt.ClntVO;
import org.warehouse.models.baseinfo.iteminfo.ItemInfoVO;
import org.warehouse.models.baseinfo.loc.LocVO;
import org.warehouse.models.baseinfo.wactr.WactrForm;
import org.warehouse.models.baseinfo.wactr.WactrVO;
import org.warehouse.models.stdin.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/stdin")
@RequiredArgsConstructor
public class StdinController {
	private final ClntDAO clntDAO;
	private final StdinDAO stdinDAO;
	private final ItemInfoDAO itemInfoDAO;
	private final LocDAO locDAO;

	private final StdinValidator validator;
	private final StdinService service;

	private final HttpServletRequest request;
	private final HttpServletResponse response;


	@GetMapping("/registertest")
	public String stdinRegisterTest(Model model) {
		List<ClntVO> list = clntDAO.getClntList();
		model.addAttribute("clntList", list);

		List<ItemInfoVO> item_list = itemInfoDAO.getItemList();
		model.addAttribute("itemList", item_list);

		List<LocVO> loc_list = locDAO.getLocList();
		model.addAttribute("locList", loc_list);

		//stdinFormList 를 넘겨줌
		StdinTestForm stdinTestForm = new StdinTestForm();

		model.addAttribute("stdinTestForm", stdinTestForm);

		return "stdin/registertest";
	}

	@PostMapping("/savetest")
	public String savetest() {


		closeLayer(response);
		return "close";
	}

	@GetMapping("/register")
	public String stdinRegister(Model model) {
		StdinForm stdinForm = new StdinForm();

		List<ClntVO> list = clntDAO.getClntList();
		model.addAttribute("clntList", list);

		List<ItemInfoVO> item_list = itemInfoDAO.getItemList();
		model.addAttribute("itemList", item_list);

		List<LocVO> loc_list = locDAO.getLocList();
		model.addAttribute("locList", loc_list);

		model.addAttribute(stdinForm);

		return "stdin/register";
	}

	@GetMapping("/update/{stdinNum}")
	public String update(@PathVariable String stdinNum, Model model) {
		StdinVO stdinVO = stdinDAO.getDetail(stdinNum);

		StdinForm stdinForm = new ModelMapper().map(stdinVO, StdinForm.class);

		model.addAttribute("stdinForm", stdinForm);

		return("stdin/update");
	}

	@PostMapping("/save")
	public String stdinRegisterPs(@Valid List<StdinForm> stdinForm, Errors errors, Model model) {
		System.out.println(stdinForm);
		/*
		validator.validate(stdinForm, errors);

		if(errors.hasErrors()) {
			List<ClntVO> list = clntDAO.getClntList();
			model.addAttribute("clntList", list);

			List<ItemInfoVO> item_list = itemInfoDAO.getItemList();
			model.addAttribute("itemList", item_list);

			List<LocVO> loc_list = locDAO.getLocList();
			model.addAttribute("locList", loc_list);

			return "stdin/register";
		}

		service.register(stdinForm);

		closeLayer(response);
*/

		return "close";
	}

	@GetMapping
	public String stdin(Model model) {
		commonProcess(model, "stdin_h");
		
		List<ClntVO> clnt_list = clntDAO.getClntList();
		model.addAttribute("clnt_list", clnt_list);

		List<StdinVO> stdin_list = stdinDAO.getList();
		model.addAttribute("stdin_list", stdin_list);



		return "stdin/list_h";
	}

	@GetMapping("/detail")
	public String stdin_detail(Model model) {
		commonProcess(model, "stdin_d");
		model.addAttribute("detailList", stdinDAO.getDetailList());
		return "stdin/list_d";
	}

	private void commonProcess(Model model, String menuCode) {
		String Title = "입고";
		String pageName = "stdin";
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
