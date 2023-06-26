package org.warehouse.controllers.baseinfo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.warehouse.configs.models.mapper.ItemInfoDAO;
import org.warehouse.controllers.admins.JoinForm;
import org.warehouse.models.baseinfoVO.ItemInfoVO;
import org.warehouse.services.ItemInfoService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/baseinfo")
public class ItemInfoController {

    private final ItemInfoDAO itemInfoDAO;
    private final ItemInfoService itemInfoService;

    @GetMapping("/iteminfo")
    public String iteminfo(Model model) {

        ItemInfoVO itemInfoVO = new ItemInfoVO();
        model.addAttribute("itemInfoVO", itemInfoVO);

        return "baseinfo/iteminfo";
    }

    @PostMapping("/iteminfo")
    public String iteminfoPs(@Valid ItemInfoVO itemInfoVO, Errors errors, Model model) {
        System.out.println("Controller :: " + itemInfoVO);


        if(errors.hasErrors()) {
            return "baseinfo/iteminfo";
        }

        itemInfoVO.setRegNm("session");
        itemInfoService.itemInfoSave(itemInfoVO);

        return "redirect:/baseinfo/iteminfo";
    }

}