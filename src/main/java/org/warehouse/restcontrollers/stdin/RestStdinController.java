package org.warehouse.restcontrollers.stdin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.warehouse.configs.models.mapper.ItemInfoDAO;
import org.warehouse.configs.models.mapper.LocDAO;
import org.warehouse.configs.models.mapper.StdinDAO;
import org.warehouse.models.baseinfo.iteminfo.ItemInfoVO;
import org.warehouse.models.baseinfo.loc.LocVO;
import org.warehouse.models.stdin.StdinVO;

import java.util.List;

@RestController("stdinAjaxController")
@RequestMapping("/stdin/ajaxstdin")
@RequiredArgsConstructor
public class RestStdinController {
	private final ItemInfoDAO itemInfoDAO;
	private final LocDAO locDAO;
	private final StdinDAO stdinDAO;

	@GetMapping
	public List<ItemInfoVO> getItemList(String clntCd) {
		List<ItemInfoVO> list = itemInfoDAO.getItemListByClntCd(clntCd);
		return list;
	}

	@GetMapping("getLoc")
	public List<LocVO> getLocList(String itemCd) {
		return locDAO.getLocListByItemCd(itemCd);
	}

	@GetMapping("getDetail")
	public StdinVO getDetail(Long stdinNum) {
		StdinVO stdinVO = stdinDAO.getDetail(stdinNum);
		return stdinVO;
	}

	@GetMapping("modify")
	public void modify(Long stdinnum) {

	}
}
