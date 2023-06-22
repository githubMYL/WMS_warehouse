package org.warehouse.controllers.admins;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.warehouse.models.user.UserJoinValidator;
import org.warehouse.services.UserJoinService;
import org.warehouse.services.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
	private final UserJoinService joinService;
	private final UserJoinValidator joinValidator;

	@GetMapping("/join")
	public String join(Model model) {
		JoinForm joinForm = new JoinForm();
		model.addAttribute("joinForm", joinForm);

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
