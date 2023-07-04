package org.warehouse.controllers.users;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final UserInfoService userInfoService;

	@GetMapping("/join")
	public String join(Model model) {
		JoinForm joinForm = new JoinForm();
		model.addAttribute("joinForm", joinForm);

		return "user/join";
	}

	@PostMapping("/join")
	public String joinPs(@Valid JoinForm joinForm, Model model) {

		return null;
	}

	@GetMapping("/login")
	public String login(Model model) {

		return "user/login";
	}


}
