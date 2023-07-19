package org.warehouse.controllers.rels.relsAction;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.warehouse.configs.models.mapper.*;
import org.warehouse.models.admin.clnt.ClntVO;
import org.warehouse.models.admin.cust.CustVO;
import org.warehouse.models.admin.custctr.CustCtrVO;
import org.warehouse.models.baseinfo.iteminfo.ItemInfoVO;
import org.warehouse.models.baseinfo.loc.LocVO;
import org.warehouse.models.baseinfo.wactr.WactrVO;
import org.warehouse.models.rels.relsAction.RelsService;
import org.warehouse.models.rels.relsAction.RelsVO;
import org.warehouse.models.rels.relsAction.RelsValidator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rels")
public class RelsController {

    private final RelsDAO relsDAO;
    private final ClntDAO clntDAO;
    private final CustDAO custDAO;
    private final CustCtrDAO custCtrDAO;
    private final ItemInfoDAO itemInfoDAO;
    private final RelsService relsService;
    private final RelsValidator relsValidator;
    private final HttpServletResponse response;

    /** 출고등록 리스트 */
    @GetMapping
    public String rels(Model model){

        commonProcess(model);

        List<RelsVO> relsList = relsDAO.relsList();
        model.addAttribute("relsList", relsList);

        List<RelsVO> relsSubDetailList = relsDAO.relsSubDetailList();
        model.addAttribute("relsSubDetailList", relsSubDetailList);

        return "rels/rels";
    }

    /** 출고등록 D 리스트 */
    @ResponseBody
    @GetMapping("relsDetail")
    public List<RelsVO> relsDetail(String relsDt, String relsNo) {

        HashMap<String,String> relsDetailMap = new HashMap<>();

        relsDt = relsDt.replaceAll("-", "");

        relsDetailMap.put("relsDt", relsDt);
        relsDetailMap.put("relsNo", relsNo);

        List<RelsVO> relsDetList = relsDAO.relsDetailList(relsDetailMap);
        //List<RelsVO> relsDetList = relsDAO.relsDetailList(relsDt, relsNo);

        return relsDetList;
    }

    /** 출고등록 S (할당) */


    /** 출고등록 수정 팝업 */
    @GetMapping("/{keyVal}/update")
    public String update(@PathVariable String keyVal, Model model) {
        commonProcess(model);

        RelsVO relsVO = new RelsVO();

        /** 고객사 코드 S */
        List<ClntVO> clntList = clntDAO.getClntList();
        model.addAttribute("clntList", clntList);
        /** 고객사 코드 E */

        /** 납품처 코드 S */
        List<CustVO> custList = custDAO.getCustList();
        model.addAttribute("custList", custList);
        /** 납품처 코드 E */

        /** 납품센터 코드 S */
        List<CustCtrVO> custCtrList = custCtrDAO.getCustCtrList();
        model.addAttribute("custCtrList", custCtrList);
        /** 납품센터 코드 E */

        /** 상품정보 코드 S */
        List<ItemInfoVO> itemInfoList = itemInfoDAO.getItemList();
        model.addAttribute("itemInfoList", itemInfoList);
        /** 상품정보 코드 E */

        relsVO = relsDAO.relsUpdataInit(keyVal);
        System.out.println("=============================================");
        System.out.println(keyVal);
        System.out.println("=============================================");
        System.out.println(relsVO);
        System.out.println("=============================================");
        model.addAttribute("relsVO", relsVO);

        return "rels/popup/relsUpdatePop";
    }

    /** 출고등록 수정 팝업 */
    @GetMapping("/{keyVal}/detInsert")
    public String detInsert(@PathVariable String keyVal, Model model) {
        commonProcess(model);

        RelsVO relsVO = new RelsVO();

        /** 고객사 코드 S */
        List<ClntVO> clntList = clntDAO.getClntList();
        model.addAttribute("clntList", clntList);
        /** 고객사 코드 E */

        /** 납품처 코드 S */
        List<CustVO> custList = custDAO.getCustList();
        model.addAttribute("custList", custList);
        /** 납품처 코드 E */

        /** 납품센터 코드 S */
        List<CustCtrVO> custCtrList = custCtrDAO.getCustCtrList();
        model.addAttribute("custCtrList", custCtrList);
        /** 납품센터 코드 E */

        /** 상품정보 코드 S */
        List<ItemInfoVO> itemInfoList = itemInfoDAO.getItemList();
        model.addAttribute("itemInfoList", itemInfoList);
        /** 상품정보 코드 E */

        relsVO = relsDAO.relsUpdataInit(keyVal);
        System.out.println("=============================================");
        System.out.println(keyVal);
        System.out.println("=============================================");
        System.out.println(relsVO);
        System.out.println("=============================================");
        model.addAttribute("relsVO", relsVO);

        return "rels/popup/relsDetInsertPop";
    }

    /** 출고등록 추가 팝업  */
    @GetMapping("/register")
    public String relsRegister(Model model){

        RelsVO relsVO = new RelsVO();

        /** 고객사 코드 S */
        List<ClntVO> clntList = clntDAO.getClntList();
        model.addAttribute("clntList", clntList);
        /** 고객사 코드 E */

        /** 납품처 코드 S */
        List<CustVO> custList = custDAO.getCustList();
        model.addAttribute("custList", custList);
        /** 납품처 코드 E */

        /** 납품센터 코드 S */
        List<CustCtrVO> custCtrList = custCtrDAO.getCustCtrList();
        model.addAttribute("custCtrList", custCtrList);
        /** 납품센터 코드 E */

        /** 상품정보 코드 S */
        List<ItemInfoVO> itemInfoList = itemInfoDAO.getItemList();
        model.addAttribute("itemInfoList", itemInfoList);
        /** 상품정보 코드 E */

        relsVO.setRegInAmt(0L);
        relsVO.setRelsCnt(0L);
        relsVO.setPlt(0L);
        relsVO.setBox(0L);
        model.addAttribute("relsVO", relsVO);

        return "rels/popup/relsPop";
    }

    /** 출고등록 추가 & 수정 처리  */
    @PostMapping("/relsSave")
    public String relsPs(@Valid RelsVO relsVO, Errors errors, Model model) {

        relsValidator.validate(relsVO, errors);

        if(errors.hasErrors()) {

            /** 고객사 코드 S */
            List<ClntVO> clntList = clntDAO.getClntList();
            model.addAttribute("clntList", clntList);
            /** 고객사 코드 E */

            /** 납품처 코드 S */
            List<CustVO> custList = custDAO.getCustList();
            model.addAttribute("custList", custList);
            /** 납품처 코드 E */

            /** 납품센터 코드 S */
            List<CustCtrVO> custCtrList = custCtrDAO.getCustCtrList();
            model.addAttribute("custCtrList", custCtrList);
            /** 납품센터 코드 E */

            /** 상품정보 코드 S */
            List<ItemInfoVO> itemInfoList = itemInfoDAO.getItemList();
            model.addAttribute("itemInfoList", itemInfoList);
            /** 상품정보 코드 E */

            return "rels/popup/relsPop";
        }


        relsService.save(relsVO);

        closeLayer(response);
        return "close";
    }

    @GetMapping("/deleteRels")
    public String deleteRels(String chkArr) {

        System.out.println("delete init =========================================");

        String[] deleteKey = chkArr.split(",");

        for(int i = 0; i < deleteKey.length; i++) {

            String hDataYn = relsDAO.relsHdelChk(deleteKey[i]);
            String dDataYn = relsDAO.relsDdelChk(deleteKey[i]);
            String sDataYn = relsDAO.relsSdelChk(deleteKey[i]);
            System.out.println("hDataYn : dDataYn : sDataYn = " + hDataYn + " : " + dDataYn + " : " + sDataYn);
            if(hDataYn != null)
                relsDAO.relsHdelete(deleteKey[i]);
            if(dDataYn != null)
                relsDAO.relsDdelete(deleteKey[i]);
            if(sDataYn != null)
                relsDAO.relsSdelete(deleteKey[i]);
        }

        return "redirect:/rels";
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
