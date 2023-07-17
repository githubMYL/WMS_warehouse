package org.warehouse.controllers.rels.relsAction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.warehouse.configs.models.mapper.RelsDAO;
import org.warehouse.models.rels.relsAction.RelsVO;
import org.warehouse.models.stdin.StdinVO;

import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rels")
public class RelsController {

    private final RelsDAO relsDAO;

    /** 출고등록 */
    @GetMapping
    private String rels(Model model){

        commonProcess(model);

        List<RelsVO> relsList = relsDAO.relsList();
        model.addAttribute("relsList", relsList);

        List<RelsVO> relsSubDetailList = relsDAO.relsSubDetailList();
        model.addAttribute("relsSubDetailList", relsSubDetailList);

        return "rels/rels";
    }

    /** 출고등록 D */
    @ResponseBody
    @GetMapping("relsDetail")
    public List<RelsVO> relsDetail(String relsDt, String relsNo) {

        HashMap<String,String> relsDetailMap = new HashMap<>();

        System.out.println("====== init ======");
        relsDt = relsDt.replaceAll("-", "");
        System.out.println("relsDt :: " + relsDt);
        System.out.println("relsNo :: " + relsNo);

        relsDetailMap.put("relsDt", relsDt);
        relsDetailMap.put("relsNo", relsNo);
        System.out.println(relsDetailMap);
        List<RelsVO> relsDetList = relsDAO.relsDetailList(relsDetailMap);
        //List<RelsVO> relsDetList = relsDAO.relsDetailList(relsDt, relsNo);
        System.out.println("relsDetList : " + relsDetList);
        System.out.println("====== init END ======");
        return relsDetList;
    }

    /** 출고등록 S (할당) */


    /** 출고등록 추가 팝업  */
    @GetMapping("/register")
    private String relsRegister(Model model){

        RelsVO relsVO = new RelsVO();
        model.addAttribute("relsVO", relsVO);
        return "rels/popup/relsPopup";

    }

    /** 출고등록 추가 & 수정 처리  */


    private void commonProcess(Model model) {
        String Title = "출고::출고등록";
        String menuCode = "rels";
        String pageName = "rels";
        model.addAttribute("Title", Title);
        model.addAttribute("menuCode", menuCode);
        model.addAttribute("pageName", pageName);
    }
}
