package org.warehouse.controllers.tmstk;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.warehouse.configs.models.mapper.*;
import org.warehouse.controllers.users.UserInfo;
import org.warehouse.models.baseinfo.loc.LocVO;
import org.warehouse.models.stock.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {
	private final StockDAO stockDAO;
	private final LocDAO locDAO;
	private final StkadjModService modService;

	private final HttpSession session;
	private final HttpServletResponse response;


	private void commonProcess(Model model) {
		String Title = "재고::시점재고";
		String menuCode = "tmstk";
		String pageName = "stock";
		model.addAttribute("pageName", pageName);
		model.addAttribute("Title", Title);
		model.addAttribute("menuCode", menuCode);
	}

	/* tmstk S */
	@GetMapping("/tmstk")
	public String tmstkList(Model model , String search_clntNm ,String search_itemCd,String search_itemNm){

		commonProcess(model);
		if (search_clntNm != null || search_itemCd!= null || search_itemNm!=null) {
			List<TmstkVO> search_list = stockDAO.getSearchList(search_clntNm,search_itemCd,search_itemNm);
			model.addAttribute("list", search_list);
		} else {
			List<TmstkVO> list = stockDAO.tmstkList();
			model.addAttribute("list", list);
		}


		return "stock/tmstk_list";
	}
	/* tmstk E */


	/* stkadj S */
	@GetMapping("/stkadj")
	public String stkadj(Model model){
		model.addAttribute("pageName", "stock");
		model.addAttribute("Title", "재고::재고조정");
		model.addAttribute("menuCode", "stkadj");

		List <StkadjForm> stkadjList = stockDAO.stkadjList();

		model.addAttribute("stkadjList",stkadjList);

		return "stock/stkadj_list";
	}


	@GetMapping("/stkadjMod/{tmstkCd}")
	public String stkadjModForm(@PathVariable String[] tmstkCd , Model model) {
		// 재고 조정을 위해 더블클릭 정보하나 가져오기

		// String 배열을 HashMap으로 변환
		HashMap<String, String> tmstkCdMap = new HashMap<>();

		tmstkCdMap.put("clnt_cd", tmstkCd[0]);
		tmstkCdMap.put("item_cd", tmstkCd[1]);
		tmstkCdMap.put("wactr_cd", tmstkCd[2]);
		tmstkCdMap.put("loc_cd", tmstkCd[3]);

//        System.out.println("확인" + tmstkCdMap.toString());

		StkadjForm stkadjFormOne = stockDAO.stkadjOne(tmstkCdMap);


		model.addAttribute("vo",stkadjFormOne);

		return "stock/stkadjModForm";
	}


	// 재고 조정 후 stkadj 정보입력 , tmstk 재고 update
	@PostMapping("/stkadjMod")
	public String stkadjMod(StkadjForm vo) {

		System.out.println(vo.toString());
		// 조정후 수량 체크
		Long after_adj_stock = vo.getModNomalAmt() - vo.getModFaultAmt() - vo.getAllo_amt();
		// tmstk 전체재고 update 수량 체크
		Long modTmstkStockAmt = (vo.getModNomalAmt() + vo.getModFaultAmt()) - vo.getStock_amt();
		// tmstk 불량재고 update 수량
		Long modTmstkFaultAmt = vo.getModFaultAmt() - vo.getFault_amt();


		System.out.println("조정전 : " + vo.getBefore_adj_stock()
				+ " 조정 후 : " + after_adj_stock
				+ " 전체재고 차이 : " + modTmstkStockAmt
				+ " 불량재고 차이 : " + modTmstkFaultAmt);


		vo.setAfter_adj_stock(after_adj_stock);
		vo.setModTmstkStockAmt(modTmstkStockAmt);
		vo.setModTmstkFaultAmt(modTmstkFaultAmt);


		modService.stkadjMod(vo);

		closeLayer(response);

		return "redirect:/stock/stkadj";
	}
	/* stkadj E */


	/* stktransf S */
	@GetMapping("/stktransf")
	public String stktransf(Model model){
		List<TmstkVO> tmstkList = stockDAO.tmstkList();
		List<StktransfVO> stktransfList = stockDAO.getStktransfList();
		System.out.println(stktransfList);

		model.addAttribute("pageName", "stock");
		model.addAttribute("Title", "재고::재고이동");
		model.addAttribute("menuCode", "stktransf");


		model.addAttribute("list", tmstkList);
		model.addAttribute("stktransfList", stktransfList);
		return "stock/stktransf_list";
	}

	@GetMapping("_transfForm")
	public String transfForm(@Param("locCd")String locCd, @Param("itemCd")String itemCd, Model model) {
		StktransfVO stktransfVO = new StktransfVO();

		TmstkVO tmstkVO = stockDAO.getTmstkByLocCdItemCd(locCd, itemCd);
		List<LocVO> locList = locDAO.getLocListByWactrCd(tmstkVO.getWactr_cd());



		model.addAttribute("tmstkVO", tmstkVO);
		model.addAttribute("locList", locList);
		model.addAttribute("stktransfVO", stktransfVO);

		return "stock/_stktransfForm";
	}

	@PostMapping("_transfForm")
	public String transfForm(StktransfVO stktransfVO, Model model) {
		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");

		TmstkVO tmstkVO = stockDAO.getTmstkByLocCdItemCd(stktransfVO.getLocCdFrom(), stktransfVO.getItemCd());
		// 재고 이동에서는 같은 물류센터로만 이동한다.
		String wactrCd = locDAO.getLocByLocCd(stktransfVO.getLocCdFrom()).getWactr_cd();

		stktransfVO.setClntCd(tmstkVO.getClnt_cd());
		stktransfVO.setWaCtrCdFrom(wactrCd);
		stktransfVO.setWaCtrCdTo(wactrCd);
		stktransfVO.setRuntimeStock(tmstkVO.getStock_amt());	// 추후에 출고쪽 물려있는 수량 뺄 예정
		stktransfVO.setRegNm(userInfo.getUserNm());
		System.out.println(stktransfVO);

		// 시점재고의 값을 변경한다.
		// 기존 로케이션에서 재고를 옮길 때, 기존 로케이션에 잔여 재고가 있는 경우
		if(tmstkVO.getStock_amt() > stktransfVO.getMoveAmt()){
			// 기존 로케이션의 재고 값을 수정한다.
			tmstkVO.setStock_amt(tmstkVO.getStock_amt()-stktransfVO.getMoveAmt());
			tmstkVO.setModNm(userInfo.getUserNm());
			stockDAO.updateTmstkAmt(tmstkVO);

			// 이동할 로케이션의 재고 값을 수정한다.
			// 만약, 옮겨갈 로케이션이 기존에 재고가 존재할 경우, 값을 추가하여 수정,
			if(stockDAO.getTmstkByLocCdItemCd(stktransfVO.getLocCdTo(), stktransfVO.getItemCd()) != null) {
				TmstkVO existTmstk = stockDAO.getTmstkByLocCdItemCd(stktransfVO.getLocCdTo(), stktransfVO.getItemCd());
				existTmstk.setStock_amt(existTmstk.getStock_amt() + stktransfVO.getMoveAmt());
				existTmstk.setModNm(userInfo.getUserNm());

				stockDAO.updateTmstkAmt(existTmstk);
			}
			// 옮겨갈 로케이션이 기존에 재고가 존재하지 않을 경우, 새로운 열을 추가
			else {
				TmstkVO toTmstk = new TmstkVO();
				toTmstk.setClnt_cd(stktransfVO.getClntCd());		// 고객사 코드
				toTmstk.setItem_cd(stktransfVO.getItemCd());		// 상품 코드
				toTmstk.setLoc_cd(stktransfVO.getLocCdTo());		// 이동할 로케이션 코드
				toTmstk.setWactr_cd(stktransfVO.getWaCtrCdTo());	// 물류센터 코드
				toTmstk.setStock_amt(stktransfVO.getMoveAmt());		// 이동 수량
				toTmstk.setFault_amt(0L);							// 불량을 이동하는 경우는 들어가있지 않았음

				toTmstk.setRegNm(userInfo.getUserNm());

				stockDAO.insertTmstk(toTmstk);
			}

		}
		// 기존 로케이션에서 재고를 옮길 때, 기존 로케이션에 잔여 재고가 없이 전부 빼가는 경우 (초과는 html 단에서 script로 걸러짐)
		else if(tmstkVO.getStock_amt() == stktransfVO.getMoveAmt()){
			// 기존 로케이션의 가용 재고를 비우고, 만약 fault_amt 또한 0이라면, 테이블을 비운다.
			tmstkVO.setStock_amt(0L);
			if(tmstkVO.getFault_amt() == 0) {
				stockDAO.deleteTmstk(tmstkVO);
			}


			// 만약, 옮겨갈 로케이션이 기존에 재고가 존재할 경우, 값을 추가하여 수정,
			if(stockDAO.getTmstkByLocCdItemCd(stktransfVO.getLocCdTo(), stktransfVO.getItemCd()) != null) {
				TmstkVO existTmstk = stockDAO.getTmstkByLocCdItemCd(stktransfVO.getLocCdTo(), stktransfVO.getItemCd());
				existTmstk.setStock_amt(existTmstk.getStock_amt() + stktransfVO.getMoveAmt());
				existTmstk.setModNm(userInfo.getUserNm());

				stockDAO.updateTmstkAmt(existTmstk);
			}
			// 옮겨갈 로케이션이 기존에 재고가 존재하지 않을 경우, 새로운 열을 추가
			else {
				TmstkVO toTmstk = new TmstkVO();
				toTmstk.setClnt_cd(stktransfVO.getClntCd());		// 고객사 코드
				toTmstk.setItem_cd(stktransfVO.getItemCd());		// 상품 코드
				toTmstk.setLoc_cd(stktransfVO.getLocCdTo());		// 이동할 로케이션 코드
				toTmstk.setWactr_cd(stktransfVO.getWaCtrCdTo());	// 물류센터 코드
				toTmstk.setStock_amt(stktransfVO.getMoveAmt());		// 이동 수량
				toTmstk.setFault_amt(0L);							// 불량을 이동하는 경우는 들어가있지 않았음

				toTmstk.setRegNm(userInfo.getUserNm());

				stockDAO.insertTmstk(toTmstk);
			}
		}

		// 재고 조정 처리가 끝난 후, 재고 조정 테이블에 insert
		stockDAO.insertStktransf(stktransfVO);

		closeLayer(response);

		return "close";
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
	/* stktransf E */
}