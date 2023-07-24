package org.warehouse.commons;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.warehouse.models.common.CommonVO;

import java.util.Map;

@Component
public class CommonProcess {

    public void commonProcess(CommonVO common, Model model) {

        model.addAttribute("pageName", common.getPageName());
        model.addAttribute("Title", common.getTitle());
        model.addAttribute("menuCode", common.getMenuCode());
    }
}
