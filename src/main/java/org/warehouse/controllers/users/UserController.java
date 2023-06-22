package org.warehouse.controllers.users;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.warehouse.configs.models.mapper.UserMapper;
import org.warehouse.controllers.admins.JoinForm;
import org.warehouse.services.UserService;

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
