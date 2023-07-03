package org.warehouse.controllers.baseinfo;


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
import org.warehouse.configs.models.mapper.WactrDAO;
import org.warehouse.models.baseinfo.wactr.WactrForm;
import org.warehouse.models.baseinfo.wactr.WactrRegisterService;
import org.warehouse.models.baseinfo.wactr.WactrVO;
import org.warehouse.models.baseinfo.wactr.WactrValidator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/baseinfo/wactr")
public class WactrController {
	private final WactrDAO wactrDAO;
	private final WactrValidator validator;
	private final WactrRegisterService registerService;

	private final HttpServletRequest request;
	private final HttpServletResponse response;

	@GetMapping
	public String wactr(Model model) {
		commonProcess(model, "기본정보");


		List<WactrVO> list = wactrDAO.getList();

		model.addAttribute("list", list);

		return "baseinfo/wactr";
	}



	@GetMapping("/admin/register")
	public String register(Model model) {
		WactrForm wactrForm = new WactrForm();

		model.addAttribute("wactrForm", wactrForm);

		return "admin/wactr/register";
	}

	@GetMapping("/admin/{cd}/update")
	public String update(@PathVariable String cd, Model model) {
		WactrVO wactrVO = wactrDAO.getWactrByCd(cd);

		WactrForm wactrForm = new ModelMapper().map(wactrVO, WactrForm.class);

		model.addAttribute("wactrForm", wactrForm);

		return("admin/wactr/update");
	}

	@PostMapping("/admin/save")
	public String save(@Valid WactrForm wactrForm, Errors errors, Model model) {
		validator.validate(wactrForm, errors);

		if(errors.hasErrors()) {
			return "admin/wactr/register";
		}

		registerService.register(wactrForm);

		closeLayer(response);


		return "redirect:/baseinfo/wactr";
	}

	private void commonProcess(Model model, String title) {
		String menuCode = "wactr";

		String subMenuCode = Menus.getSubMenuCode(request);
		subMenuCode = title.equals("기본정보") ? "baseinfo" : subMenuCode;
		model.addAttribute("subMenuCode", subMenuCode);

		List<MenuDetail> submenus = Menus.gets("baseinfo");
		model.addAttribute("submenus", submenus);


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
