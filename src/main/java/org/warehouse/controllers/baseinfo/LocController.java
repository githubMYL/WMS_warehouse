package org.warehouse.controllers.baseinfo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.warehouse.configs.models.mapper.LocDAO;
import org.warehouse.configs.models.mapper.WactrDAO;
import org.warehouse.controllers.users.UserInfo;
import org.warehouse.models.baseinfo.loc.LocVO;
import org.warehouse.models.baseinfo.wactr.WactrVO;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/baseinfo")
public class LocController {

	private final WactrDAO wactrDAO;
	private final LocDAO locDAO;
	private final HttpSession session;

	//loc 리스트
	@GetMapping("/loc")
	public String loc(@RequestParam(name = "search_loc" ,required = false) String search_loc, Model model){


		if( search_loc != null ){
			List<LocVO> search_list = locDAO.getSearchList(search_loc);
			model.addAttribute("loc_list" , search_list);
		} else{
			List<LocVO> loc_list = locDAO.getLocList();
			model.addAttribute("loc_list",loc_list);
		}

		return "baseinfo/loc";
	}

	// loc 등록 폼
	@GetMapping("/locjoin")
	public String locForm (Model model ){
		List<WactrVO> waclist = wactrDAO.getList();

		model.addAttribute("wactr_list", waclist);
		return "baseinfo/locForm";
	}

	// loc 등록
	@PostMapping("locjoin")
	public String locJoin(LocVO loc){
		UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

		String loc_cd = loc.getLoc_nm() +"-"+ loc.getLoc_addr();

		loc.setLoc_cd(loc_cd);
		loc.setReg_nm(userInfo.getUserNm());

		locDAO.insertLoc(loc);


		return "redirect:/baseinfo/loc";
	}

	@GetMapping("locDelte")
	public String loc_delete(String loc_cd_array){
		System.out.println(loc_cd_array);
		String[] loc_cd = loc_cd_array.split(",");

		for(int i=0; i<loc_cd.length; i++) {
			locDAO.deleteLoc(loc_cd[i]);
		}
		return "redirect:/baseinfo/loc";
	}


	@GetMapping("/locMod")
	public String locMod(String loc_cd_array,Model model){


		LocVO modVO = locDAO.modLoc(loc_cd_array);

		model.addAttribute("modVO", modVO);

		return "baseinfo/locModForm";
	}
}