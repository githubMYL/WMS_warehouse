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
import org.warehouse.configs.models.mapper.ItemInfoDAO;
import org.warehouse.configs.models.mapper.LocDAO;
import org.warehouse.configs.models.mapper.StdinDAO;
import org.warehouse.models.admin.clnt.ClntVO;
import org.warehouse.models.baseinfo.iteminfo.ItemInfoVO;
import org.warehouse.models.baseinfo.loc.LocVO;
import org.warehouse.models.stdin.StdinForm;
import org.warehouse.models.stdin.StdinService;
import org.warehouse.models.stdin.StdinVO;
import org.warehouse.models.stdin.StdinValidator;

import java.time.LocalDate;
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

	@PostMapping("/register")
	public String stdinRegisterPs(@Valid StdinForm stdinForm, Errors errors, Model model) {
		validator.validate(stdinForm, errors);

		System.out.println("stdinForm : " + stdinForm);

		if(errors.hasErrors()) {
			return "stdin/register";
		}

		service.register(stdinForm);


		return "redirect:/stdin";
	}

	@GetMapping
	public String stdin(Model model) {
		List<ClntVO> clnt_list = clntDAO.getClntList();
		model.addAttribute("clnt_list", clnt_list);

		List<StdinVO> stdin_list = stdinDAO.getList();
		model.addAttribute("stdin_list", stdin_list);



		return "stdin/list_h";
	}
}
