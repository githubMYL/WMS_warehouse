package org.warehouse.controllers.tmstk;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.warehouse.configs.models.mapper.*;
import org.warehouse.controllers.users.UserInfo;
import org.warehouse.models.baseinfo.loc.LocVO;
import org.warehouse.models.stock.StkadjForm;
import org.warehouse.models.stock.StkadjModService;
import org.warehouse.models.stock.StktransfVO;
import org.warehouse.models.stock.TmstkVO;


import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
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
	public String tmstkList(Model model, @Param("search_clntNm") String search_clntNm, @Param("search_itemCd") String search_itemCd, @Param("search_itemNm") String search_itemNm){

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
	public String stkadj(Model model ,
						 @RequestParam(name = "search_tmstk_wactrNm", required = false) String search_tmstk_wactrNm,
						 @RequestParam(name = "search_tmstk_clntNm", required = false) String search_tmstk_clntNm,
						 @RequestParam(name = "search_tmstk_locCd", required = false) String search_tmstk_locCd,
						 @RequestParam(name = "search_tmstk_itemNm", required = false) String search_tmstk_itemNm) {

		model.addAttribute("pageName", "stock");
		model.addAttribute("Title", "재고::재고조정");
		model.addAttribute("menuCode", "stkadj");



		// 로그인 정보 가져오기
		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");

//        System.out.println("확인: " + userInfo.getUserType());
		if(userInfo!=null){
			// 로그인정보가 존재할 시 타입만 가져와서 넣어주기
			model.addAttribute("loginUser",userInfo.getUserType());
		} else {
			// 없을시 NotLogin 이라는 메세지 바인딩
			model.addAttribute("loginUser","NotLogin");
		}


		// 검색에 따라 바인딩을 다르게한다
		if((search_tmstk_wactrNm+search_tmstk_clntNm+ search_tmstk_locCd+search_tmstk_itemNm).isEmpty()
				||search_tmstk_wactrNm == null && search_tmstk_clntNm == null && search_tmstk_locCd == null && search_tmstk_itemNm == null) {
			List <StkadjForm> stkadjList = stockDAO.tmsktList();
			model.addAttribute("stkadjList", stkadjList);
		} else {

			List <StkadjForm> search_stkadjList = stockDAO.search_tmstkList(search_tmstk_wactrNm,search_tmstk_clntNm,search_tmstk_locCd,search_tmstk_itemNm);
			model.addAttribute("stkadjList",search_stkadjList);

		}

		return "stock/stkadj";
	}


	@GetMapping("/stkadjMod/{tmstkCd}")
	public String stkadjModForm(@PathVariable String[] tmstkCd ,Model model) {

		// 재고 조정을 위해 더블클릭 정보하나 가져오기
		// String 배열을 HashMap으로 변환
		HashMap<String, String> tmstkCdMap = new HashMap<>();

		tmstkCdMap.put("clnt_cd", tmstkCd[0]);
		tmstkCdMap.put("item_cd", tmstkCd[1]);
		tmstkCdMap.put("wactr_cd", tmstkCd[2]);
		tmstkCdMap.put("loc_cd", tmstkCd[3]);

//        System.out.println("확인" + tmstkCdMap.toString());

		StkadjForm stkadjFormOne = stockDAO.stkadjOne(tmstkCdMap);


//        System.out.println("vo확인" + stkadjFormOne.toString());

		model.addAttribute("vo", stkadjFormOne);

		return "stock/stkadjModForm";
	}


	// 재고 조정 후 stkadj 정보입력 , tmstk 재고 update
	@PostMapping("/stkadjMod")
	public String stkadjMod(StkadjForm vo) {

		// 조정후 수량 체크
		Long after_adj_stock = vo.getModNomalAmt() + vo.getModFaultAmt() - vo.getAllo_amt();


//        System.out.println("원래 정상 : " + vo.getOrigin_nomalStock());
//        System.out.println("바뀐정상 : " + vo.getModNomalAmt());
//        System.out.println("원래 스톡 : " + vo.getStock_amt());
		// tmstk 전체재고 update 수량 체크
		Long modTmstkStockAmt = vo.getModNomalAmt()-vo.getOrigin_nomalStock();
		// tmstk 불량재고 update 수량
		Long modTmstkFaultAmt = vo.getModFaultAmt() - vo.getFault_amt();


		System.out.println("조정전 가용수량: " + vo.getBefore_adj_stock()
				+ " 조정 후 가용수량 : " + after_adj_stock
				+ " 정상재고 차이 : " + modTmstkStockAmt
				+ " 불량재고 차이 : " + modTmstkFaultAmt);


		vo.setAfter_adj_stock(after_adj_stock);
		vo.setModTmstkStockAmt(modTmstkStockAmt);
		vo.setModTmstkFaultAmt(modTmstkFaultAmt);

		System.out.println(vo.toString());

		modService.stkadjMod(vo);



		closeLayer(response);

		return "close";
	}
	/* stkadj E */


	@GetMapping("stkadjList")
	public String stkadjList(Model model,
							 @RequestParam(name = "search_tmstk_wactrNm", required = false) String search_tmstk_wactrNm,
							 @RequestParam(name = "search_tmstk_clntNm", required = false) String search_tmstk_clntNm,
							 @RequestParam(name = "search_tmstk_locCd", required = false) String search_tmstk_locCd,
							 @RequestParam(name = "search_tmstk_itemNm", required = false) String search_tmstk_itemNm,
							 @RequestParam(name = "mod_dt_start", required = false) String mod_dt_start,
							 @RequestParam(name = "mod_dt_end", required = false) String mod_dt_end){

		model.addAttribute("pageName", "stock");
		model.addAttribute("Title", "재고::조정내역");
		model.addAttribute("menuCode", "stkadjList");


		System.out.println(mod_dt_end + "::" + mod_dt_start);


		// 검색에 따라 바인딩을 다르게한다
		if((search_tmstk_wactrNm+search_tmstk_clntNm+ search_tmstk_locCd+search_tmstk_itemNm+mod_dt_end+mod_dt_start).isEmpty()
				||search_tmstk_wactrNm == null && search_tmstk_clntNm == null && search_tmstk_locCd == null && search_tmstk_itemNm == null && mod_dt_end == null && mod_dt_start == null) {

			List <StkadjForm> stkadjList = stockDAO.stkadjList();
			model.addAttribute("stkadjList", stkadjList);

		} else {

			List <StkadjForm> search_stkadjList = stockDAO.search_stkadjList(search_tmstk_wactrNm,search_tmstk_clntNm,search_tmstk_locCd,search_tmstk_itemNm,mod_dt_start,mod_dt_end);
			model.addAttribute("stkadjList",search_stkadjList);

		}


		return "stock/stkadj_list";
	}
	/* stkadj E */


	/* stktransf S */
	@GetMapping("/stktransf")
	public String stktransf(@Param("search_clntNm")String search_clntNm, @Param("search_itemCd")String search_itemCd, @Param("search_itemNm")String search_itemNm, Model model){
		model.addAttribute("pageName", "stock");
		model.addAttribute("Title", "재고::재고이동");
		model.addAttribute("menuCode", "stktransf");


		if (search_clntNm != null || search_itemCd!= null || search_itemNm!=null) {
			List<TmstkVO> search_list = stockDAO.getSearchList(search_clntNm,search_itemCd,search_itemNm);
			model.addAttribute("list", search_list);
		} else {
			List<TmstkVO> list = stockDAO.tmstkList();
			model.addAttribute("list", list);
		}


		return "stock/stktransf";
	}



	@GetMapping("_transfForm")
	public String transfForm(@Param("locCd")String locCd, @Param("itemCd")String itemCd, @Param("wactrCd")String wactrCd, Model model) {
		StktransfVO stktransfVO = new StktransfVO();
		//기본값
		stktransfVO.setMoveNormal(0L);
		stktransfVO.setMoveFault(0L);


		TmstkVO tmstkVO = stockDAO.getTmstkByLocCdItemCd(locCd, itemCd);
		List<LocVO> locList = locDAO.getLocListByWactrCd(tmstkVO.getWactr_cd());


		model.addAttribute("wactrCd", wactrCd);
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
		String wactrCd = stktransfVO.getWactrCd();

		stktransfVO.setClntCd(tmstkVO.getClnt_cd());
		stktransfVO.setWaCtrCdFrom(wactrCd);
		stktransfVO.setWaCtrCdTo(wactrCd);
		stktransfVO.setRuntimeStock(tmstkVO.getStock_amt());	// 추후에 출고쪽 물려있는 수량 뺄 예정
		stktransfVO.setRegNm(userInfo.getUserNm());

		stktransfVO.setMoveAmt(stktransfVO.getMoveNormal()+stktransfVO.getMoveFault());		// 이동수량 = 정상이동수량 + 불량이동수량

		// 시점재고의 값을 변경한다.
		// 기존 로케이션에서 재고를 옮길 때, 기존 로케이션에 잔여 재고가 있는 경우
		if(tmstkVO.getStock_amt() > stktransfVO.getMoveAmt()){
			// 기존 로케이션의 재고 값을 수정한다.
			tmstkVO.setFault_amt(tmstkVO.getFault_amt()-stktransfVO.getMoveFault());
			tmstkVO.setStock_amt(tmstkVO.getStock_amt()-stktransfVO.getMoveAmt());
			tmstkVO.setModNm(userInfo.getUserNm());
			stockDAO.updateTmstkAmt(tmstkVO);

			// 이동할 로케이션의 재고 값을 수정한다.
			// 만약, 옮겨갈 로케이션이 기존에 재고가 존재할 경우, 값을 추가하여 수정,
			if(stockDAO.getTmstkByLocCdItemCd(stktransfVO.getLocCdTo(), stktransfVO.getItemCd()) != null) {
				TmstkVO existTmstk = stockDAO.getTmstkByLocCdItemCd(stktransfVO.getLocCdTo(), stktransfVO.getItemCd());
				existTmstk.setStock_amt(existTmstk.getStock_amt() + stktransfVO.getMoveAmt());
				existTmstk.setFault_amt(existTmstk.getFault_amt() + stktransfVO.getMoveFault());
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
				toTmstk.setFault_amt(stktransfVO.getMoveFault());	// 불량 수량

				toTmstk.setRegNm(userInfo.getUserNm());

				// 기존에 tmstk에 delyn = y로 처리된 값이 있을 경우, 키 중복 발생
				// 조회 후, tmstk 테이블에 값이 존재할 경우, delyn=n 으로 update
				if(stockDAO.getTmstkByConditions(tmstkVO.getWactr_cd(), tmstkVO.getClnt_cd(), tmstkVO.getItem_cd(), tmstkVO.getLoc_cd()) != null) {
					stockDAO.updateTmstkDelyn(toTmstk);
				} else {
					// 조회 후 값이 없을 경우, 새롭게 insert
					stockDAO.insertTmstk(toTmstk);
				}
			}

		}
		// 기존 로케이션에서 재고를 옮길 때, 기존 로케이션에 잔여 재고가 없이 전부 빼가는 경우 (초과는 html 단에서 script로 걸러짐)
		else if(tmstkVO.getStock_amt() == stktransfVO.getMoveAmt()){
			// 기존 로케이션의 가용 재고를 비우고, 만약 fault_amt 또한 0이라면, 테이블을 비운다.
			tmstkVO.setStock_amt(0L);
			tmstkVO.setFault_amt(0L);
			stockDAO.deleteTmstk(tmstkVO);


			// 만약, 옮겨갈 로케이션이 기존에 재고가 존재할 경우, 값을 추가하여 수정,
			if(stockDAO.getTmstkByLocCdItemCd(stktransfVO.getLocCdTo(), stktransfVO.getItemCd()) != null) {
				TmstkVO existTmstk = stockDAO.getTmstkByLocCdItemCd(stktransfVO.getLocCdTo(), stktransfVO.getItemCd());
				existTmstk.setStock_amt(existTmstk.getStock_amt() + stktransfVO.getMoveAmt());
				existTmstk.setFault_amt(existTmstk.getFault_amt() + stktransfVO.getMoveFault());
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
				toTmstk.setFault_amt(stktransfVO.getMoveFault());	// 불량 수량

				toTmstk.setRegNm(userInfo.getUserNm());


				// 기존에 tmstk에 delyn = y로 처리된 값이 있을 경우, 키 중복 발생
				// 조회 후, tmstk 테이블에 값이 존재할 경우, delyn=n 으로 update
				if(stockDAO.getTmstkByConditions(tmstkVO.getWactr_cd(), tmstkVO.getClnt_cd(), tmstkVO.getItem_cd(), tmstkVO.getLoc_cd()) != null) {
					stockDAO.updateTmstkDelyn(toTmstk);
				} else {
					// 조회 후 값이 없을 경우, 새롭게 insert
					stockDAO.insertTmstk(toTmstk);
				}

			}
		}

		// 재고 조정 처리가 끝난 후, 재고 조정 테이블에 insert
		stockDAO.insertStktransf(stktransfVO);

		closeLayer(response);

		return "close";
	}

	// 재고이동내역
	@GetMapping("/stktransf/detail")
	public String stktransf_detail(@Param("search_moveDt") LocalDate search_moveDt, @Param("search_itemCd")String search_itemCd, @Param("search_itemNm")String search_itemNm, Model model) {
		model.addAttribute("pageName", "stock");
		model.addAttribute("Title", "재고::재고이동내역");
		model.addAttribute("menuCode", "stktransf_detail");

		if (search_moveDt != null || search_itemCd!= null || search_itemNm!=null) {
			System.out.println("search_moveDt: " + search_moveDt);
			System.out.println("search_itemCd: " + search_itemCd);
			System.out.println("search_itemNm: " + search_itemNm);
			List<StktransfVO> search_list = stockDAO.searchTransf(search_moveDt, search_itemCd, search_itemNm);
			System.out.println(search_list);
			model.addAttribute("stktransfList", search_list);
		} else {
			List<StktransfVO> list = stockDAO.getStktransfList();
			model.addAttribute("stktransfList", list);
		}

		return "stock/stktransf_list";
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