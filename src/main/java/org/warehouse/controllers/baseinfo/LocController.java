package org.warehouse.controllers.baseinfo;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.warehouse.configs.models.mapper.LocDAO;
import org.warehouse.configs.models.mapper.WactrDAO;
import org.warehouse.controllers.users.UserInfo;
import org.warehouse.models.baseinfo.loc.LocJoinValidator;
import org.warehouse.models.baseinfo.loc.LocVO;
import org.warehouse.models.baseinfo.wactr.WactrVO;
import org.springframework.ui.Model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/baseinfo")
public class LocController {


	private final WactrDAO wactrDAO;
	private final LocDAO locDAO;
	private final HttpSession session;
	private final LocJoinValidator validator;

	private final HttpServletResponse response;

	//loc 리스트
	//loc 리스트
	@GetMapping("/loc")
	public String loc(@RequestParam(name = "search_loc", required = false) String search_loc, Model model) {

		commonProcess(model);

		if (search_loc != null) {
			List<LocVO> search_list = locDAO.getSearchList(search_loc);
			model.addAttribute("loc_list", search_list);
		} else {
			List<LocVO> loc_list = locDAO.getLocList();
			model.addAttribute("loc_list", loc_list);
		}

		return "baseinfo/loc";
	}

	// loc 등록 폼
	@GetMapping("/locjoin")
	public String locForm(Model model) {

		LocVO locVO = new LocVO();

		List<WactrVO> waclist = wactrDAO.getList();

		model.addAttribute("locVO",locVO);
		model.addAttribute("wactr_list", waclist);
		return "baseinfo/popup/locForm";
	}

	// loc 등록
	@PostMapping("/locjoin")
	public String locJoin(@Valid LocVO loc,Errors errors,Model model) {

		validator.validate(loc, errors);

		if (errors.hasErrors()) {
			// 유효성 검사 오류가 있는 경우 처리 로직 추가
			// 오류가나면 datalist를 못받아오기 때문에 다시 바인딩 해주자
			List<WactrVO> waclist = wactrDAO.getList();
			model.addAttribute("wactr_list", waclist);

			System.out.println(loc.toString());

			return "baseinfo/popup/locForm";

		}


		UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

		String loc_cd = loc.getLoc_nm() + "-" + loc.getLoc_addr();
		loc.setLoc_cd(loc_cd);
		loc.setReg_nm(userInfo.getUserNm());



		locDAO.insertLoc(loc);

		closeLayer(response);
		return "redirect:/baseinfo/loc";
	}

	// 체크한 loc삭제
	@GetMapping("locDelte")
	public String loc_delete(String loc_cd_array) {

		System.out.println(loc_cd_array);
		String[] loc_cd = loc_cd_array.split(",");


		for (int i = 0; i < loc_cd.length; i++) {

			locDAO.deleteLoc(loc_cd[i]);

		}


		return "redirect:/baseinfo/loc";
	}

	// 더블클릭한 loc_cd의 정보 수정페이지
	@GetMapping("/locMod/{loc_cd}")
	public String locModForm(@PathVariable String loc_cd, Model model) {

		List<WactrVO> waclist = wactrDAO.getList();
		model.addAttribute("wactr_list", waclist);

		LocVO vo = locDAO.locCdVo(loc_cd);
		model.addAttribute("locVO",vo);



		return "baseinfo/popup/locModForm";
	}


	// loc 한개 업데이트(loc_cd)
	@PostMapping("/locMod/update")
	public String locMod(@Valid LocVO loc,Errors errors ,Model model){

		validator.validate(loc, errors);

		if (errors.hasErrors()) {
			// 유효성 검사 오류가 있는 경우 처리 로직 추가
			// 오류가나면 datalist를 못받아오기 때문에 다시 바인딩 해주자
			List<WactrVO> waclist = wactrDAO.getList();
			model.addAttribute("wactr_list", waclist);



			return "baseinfo/popup/locModForm";

		}


		UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

		String loc_cd = loc.getLoc_nm() + "-" + loc.getLoc_addr();



		loc.setLoc_cd(loc_cd);
		loc.setMod_nm(userInfo.getUserNm());



		locDAO.modLoc(loc);

		closeLayer(response);
		return "redirect:/baseinfo/loc";
	}




	// 레이어 팝업닫기
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


	private void commonProcess(Model model) {
		String Title = "기본정보::로케이션정보";
		String menuCode = "loc";
		String pageName = "baseinfo";
		model.addAttribute("pageName", pageName);
		model.addAttribute("Title", Title);
		model.addAttribute("menuCode", menuCode);
	}
}
