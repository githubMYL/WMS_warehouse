package org.warehouse.controllers.rels.relsAction;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.warehouse.configs.models.mapper.RelsDAO;
import org.warehouse.models.rels.relsAction.RelsVO;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rels")
public class RelsController {

    private final RelsDAO relsDAO;

    @GetMapping()
    private String rels(Model model){

        commonProcess(model);

        List<RelsVO> relsList = relsDAO.relsList();
        model.addAttribute("relsList", relsList);

        List<RelsVO> relsDetailList = relsDAO.relsDetailList();
        model.addAttribute("relsDetailList", relsDetailList);

        List<RelsVO> relsSubDetailList = relsDAO.relsSubDetailList();
        model.addAttribute("relsSubDetailList", relsSubDetailList);

        return "rels/rels";
    }

    private void commonProcess(Model model) {
        String Title = "출고::출고등록";
        String menuCode = "rels";
        String pageName = "rels";
        model.addAttribute("Title", Title);
        model.addAttribute("menuCode", menuCode);
        model.addAttribute("pageName", pageName);
    }


}
