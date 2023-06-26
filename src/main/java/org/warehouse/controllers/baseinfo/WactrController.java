package org.warehouse.controllers.baseinfo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.warehouse.configs.models.mapper.WactrDAO;
import org.warehouse.controllers.admins.JoinForm;
import org.warehouse.models.wactr.WactrForm;
import org.warehouse.models.wactr.WactrVO;
import org.warehouse.models.wactr.WactrValidator;
import org.warehouse.services.WactrRegisterService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/baseinfo/wactr")
public class WactrController {
	private final WactrDAO wactrDAO;
	private final WactrValidator validator;
	private final WactrRegisterService registerService;

	@GetMapping
	public String wactr(Model model) {
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
		System.out.println(wactrForm);

		validator.validate(wactrForm, errors);

		if(errors.hasErrors()) {
			return "admin/wactr/register";
		}

		registerService.register(wactrForm);

		return "redirect:/baseinfo/wactr";
	}

}
