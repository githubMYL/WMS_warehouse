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
	public String savetest(@Valid StdinTestForm stdinTestForm, Errors errors, Model model) {
		int cnt = (int)stdinTestForm.getItemData().chars().filter(s -> s == '/').count();

		StdinForm[] forms = new StdinForm[cnt];

		for(int j = 0; j < forms.length; j++) forms[j] = new StdinForm();
		for(int i = 0; i < forms.length; i++) {
			forms[i].setStdinDt(stdinTestForm.getStdinDt());
			forms[i].setClntCd(stdinTestForm.getClntCd());
			forms[i].setClntNm(stdinTestForm.getClntNm());
			forms[i].setStatus(stdinTestForm.getStatus());
		}

		String[] itemDatas = stdinTestForm.getItemData().split("/");
		String[][] itemData = new String[cnt][];
		for(int r = 0; r < cnt; r++) {
			itemData[r] = itemDatas[r].split(",");
		}

		for(int values = 0; values < cnt; values++) {
				forms[values].setItemCd(itemData[values][0]);
				forms[values].setItemNm(itemData[values][1]);
				forms[values].setLocCd(itemData[values][2]);
				forms[values].setBeforeStdin(Long.parseLong(itemData[values][3]));
				forms[values].setNormal(Long.parseLong(itemData[values][4]));
				forms[values].setFault(Long.parseLong(itemData[values][5]));
		}

		// forms 배열

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


	@GetMapping("/update/{stdinNum}/{stdinNo}")
	public String update(@PathVariable Long stdinNum, @PathVariable Long stdinNo, Model model) {
		StdinVO stdinVO = stdinDAO.getDetailByNumNo(stdinNum, stdinNo);

		StdinForm stdinForm = new ModelMapper().map(stdinVO, StdinForm.class);

		model.addAttribute("stdinForm", stdinForm);

		return("stdin/update");
	}


	@PostMapping("/save")
	public String stdinRegisterPs(@Valid StdinForm stdinForms, Errors errors, Model model) {
		if(stdinForms.getFlag() == null || stdinForms.getFlag().isEmpty() || stdinForms.getFlag().isBlank()) {
			System.out.println("작성 탐");
			int cnt = (int)stdinForms.getItemData().chars().filter(s -> s == '/').count();

			StdinForm[] forms = new StdinForm[cnt];

			for(int j = 0; j < forms.length; j++) forms[j] = new StdinForm();
			for(int i = 0; i < forms.length; i++) {
				forms[i].setStdinDt(stdinForms.getStdinDt());
				forms[i].setClntCd(stdinForms.getClntCd());
				forms[i].setClntNm(stdinForms.getClntNm());
				forms[i].setStatus(stdinForms.getStatus());
			}

			String[] itemDatas = stdinForms.getItemData().split("/");
			String[][] itemData = new String[cnt][];
			for(int r = 0; r < cnt; r++) {
				itemData[r] = itemDatas[r].split(",");
			}

			for(int values = 0; values < cnt; values++) {
				forms[values].setItemCd(itemData[values][0]);
				forms[values].setItemNm(itemData[values][1]);
				forms[values].setLocCd(itemData[values][2]);
				forms[values].setBeforeStdin(Long.parseLong(itemData[values][3]));
				forms[values].setNormal(Long.parseLong(itemData[values][4]));
				forms[values].setFault(Long.parseLong(itemData[values][5]));
			}

			service.register(forms);
		} else {
			System.out.println("수정탐");
			service.register(stdinForms);
		}

		closeLayer(response);

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
