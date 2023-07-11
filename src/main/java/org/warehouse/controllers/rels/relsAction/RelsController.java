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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping("/rels")
@RequiredArgsConstructor
public class RelsController {

    private final RelsDAO relsDAO;

    @GetMapping()
    private String rels(@ModelAttribute() Model model){

        commonProcess(model);

        List<RelsVO> relsList = relsDAO.relsList();
        model.addAttribute("relsList", relsList);

//        List<RelsVO> relsDetailList = relsDAO.relsDetailList();

//        List<RelsVO> relsSubDetailList = relsDAO.relsSubDetailList();

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
