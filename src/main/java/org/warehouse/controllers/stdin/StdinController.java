package org.warehouse.controllers.stdin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.warehouse.configs.models.mapper.ClntDAO;
import org.warehouse.models.admin.clnt.ClntVO;
import org.warehouse.models.stdin.StdinForm;
import org.warehouse.models.stdin.StdinService;
import org.warehouse.models.stdin.StdinValidator;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/stdin")
@RequiredArgsConstructor
public class StdinController {
	private final ClntDAO clntDAO;
	private final StdinValidator validator;
	private final StdinService service;

	@GetMapping("/register")
	public String stdinRegister(Model model) {
		StdinForm stdinForm = new StdinForm();

		List<ClntVO> list = clntDAO.getClntList();
		model.addAttribute("clntList", list);

		model.addAttribute(stdinForm);

		return "stdin/register";
	}

	@PostMapping("/register")
	public String stdinRegisterPs(@Valid StdinForm stdinForm, Errors errors, Model model) {
		validator.validate(stdinForm, errors);

		if(errors.hasErrors()) {
			return "stdin/register";
		}



		return null;
	}
}
