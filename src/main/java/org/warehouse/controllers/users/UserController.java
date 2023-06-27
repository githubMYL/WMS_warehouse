package org.warehouse.controllers.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final UserInfoService userInfoService;

	@GetMapping("/login")
	public String login(Model model) {

		return "user/login";
	}
}
