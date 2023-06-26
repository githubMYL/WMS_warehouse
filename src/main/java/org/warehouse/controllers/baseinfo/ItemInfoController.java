package org.warehouse.controllers.baseinfo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.warehouse.controllers.admins.JoinForm;
import org.warehouse.models.clnt.ClntVO;
import org.warehouse.models.cust.CustVO;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/baseinfo")
public class ItemInfoController {

    @GetMapping("/iteminfo")
    public String iteminfo(Model model) {

        return "baseinfo/iteminfo";
    }

    @PostMapping("/iteminfo")
    public String iteminfoPs(@Valid JoinForm joinForm, Errors errors, Model model) {

        if(errors.hasErrors()) {
            return "baseinfo/iteminfo";
        }

        return "redirect:/baseinfo/itemInfo";
    }

}