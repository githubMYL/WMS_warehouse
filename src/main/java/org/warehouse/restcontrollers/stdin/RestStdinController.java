package org.warehouse.restcontrollers.stdin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.warehouse.configs.models.mapper.ItemInfoDAO;
import org.warehouse.configs.models.mapper.LocDAO;
import org.warehouse.configs.models.mapper.StdinDAO;
import org.warehouse.configs.models.mapper.StockDAO;
import org.warehouse.models.baseinfo.iteminfo.ItemInfoVO;
import org.warehouse.models.baseinfo.loc.LocVO;
import org.warehouse.models.stdin.StdinVO;
import org.warehouse.models.stock.TmstkVO;

import java.time.LocalDate;
import java.util.List;

@RestController("stdinAjaxController")
@RequestMapping("/stdin/ajaxstdin")
@RequiredArgsConstructor
public class RestStdinController {
	private final ItemInfoDAO itemInfoDAO;
	private final LocDAO locDAO;
	private final StdinDAO stdinDAO;
	private final StockDAO stockDAO;

	@GetMapping
	public List<ItemInfoVO> getItemList(String clntCd) {
		List<ItemInfoVO> list = itemInfoDAO.getItemListByClntCd(clntCd);
		return list;
	}

	@GetMapping("getLoc")
	public List<LocVO> getLocList(String itemCd) {
		ItemInfoVO itemInfoVO = itemInfoDAO.getItem(itemCd);

		return locDAO.getLocListByWactrCd(itemInfoVO.getWactrCd());
	}

	@GetMapping("getDetail")
	public StdinVO[] getDetail(String stdinNum) {
		StdinVO[] stdinVO = stdinDAO.getDetail(stdinNum);
		return stdinVO;
	}

	@GetMapping("getStdinSearch")
	public List<StdinVO> getStdinSearch(LocalDate stdinDt, String clntNm, String status) {
		return stdinDAO.getListByConditions(stdinDt, clntNm, status);
	}

	@GetMapping("delete")
	public void delete(String stdinNum) {
		String[] stdinNumList = stdinNum.split(",");

		for(int i = 0; i < stdinNumList.length; i++) {
			stdinDAO.deleteHeaderStdin(stdinNumList[i]);
			stdinDAO.deleteDetailStdin(stdinNumList[i]);
		}
	}

	@GetMapping("detail/search")
	public List<StdinVO> detailSearch(LocalDate stdinDt, String clntNm, String itemCd, String itemNm) {
		return stdinDAO.getDetailListByConditions(stdinDt, clntNm, itemCd, itemNm);
	}


	@GetMapping("confirm")
	public void confirm(String stdinNum) {
		String[] stdinNumList = stdinNum.split(",");

		for(int i = 0; i < stdinNumList.length; i++) {
			stdinDAO.confirmItems(stdinNum);
		}

		/* 입고 확정 후 재고 반영 */
		// 1. 기존 재고 테이블의 존재여부 확인(물류센터, 고객사코드, 상품코드, 로케이션)
		// 2-1. 컬럼명 조건이 일치하는 재고 테이블이 존재할 경우, 기존값을 가져와 새로운 값을 추가하여 UPDATE
		// 2-2. 컬럼명 조건이 일치하는 재고 테이블이 존재하지 않을 경우, 재고 테이블애 새로운 값으로 INSERT
		StdinVO[] stdinVO = stdinDAO.getDetail(stdinNum);
		for(int i = 0; i < stdinVO.length; i++) {
			TmstkVO tmstk = stockDAO.getTmstkByConditions(stdinVO[i].getWactrCd(), stdinVO[i].getClntCd(), stdinVO[i].getItemCd(), stdinVO[i].getLocCd());

			if(tmstk == null) {		// 2-1. 재고 테이블이 존재하지 않을 경우
				stockDAO.insertStdin(stdinVO[i]);
			} else {				// 2-2. 재고 테이블이 존재할 경우
				stdinVO[i].setFault(tmstk.getFault_amt() + stdinVO[i].getFault());
				stdinVO[i].setBeforeStdin(tmstk.getStock_amt() + stdinVO[i].getBeforeStdin());
				stockDAO.updateStdin(stdinVO[i]);
			}
		}

	}
}
