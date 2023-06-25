package org.warehouse.controllers.baseinfo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.warehouse.configs.models.mapper.WactrDAO;
import org.warehouse.controllers.admins.JoinForm;
import org.warehouse.models.wactr.WactrForm;
import org.warehouse.models.wactr.WactrValidator;
import org.warehouse.services.WactrRegisterService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/baseinfo/wactr")
public class WactrController {
	private final WactrDAO wactrDAO;
	private final WactrValidator validator;
	private final WactrRegisterService registerService;

	@GetMapping("/admin/register")
	public String register(Model model) {
		WactrForm wactrForm = new WactrForm();

		model.addAttribute("wactrForm", wactrForm);

		return "admin/wactr/register";
	}

	@PostMapping("/admin/register")
	public String registerPs(@Valid WactrForm wactrForm, Errors errors, Model model) {
		System.out.println(wactrForm);

		validator.validate(wactrForm, errors);

		if(errors.hasErrors()) {
			return "admin/wactr/register";
		}

		registerService.register(wactrForm);

		return "user/login";
	}

}
