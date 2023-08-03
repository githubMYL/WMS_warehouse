package org.warehouse.controllers.rels.relsAction;

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
import org.warehouse.models.rels.relsAction.RelsService;
import org.warehouse.models.rels.relsAction.RelsVO;
import org.warehouse.models.rels.relsAction.RelsValidator;
import org.warehouse.models.stock.TmstkVO;

import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rels")
public class RelsController {

    private final RelsDAO relsDAO;
    private final ClntDAO clntDAO;
    private final CustDAO custDAO;
    private final CustCtrDAO custCtrDAO;
    private final ItemInfoDAO itemInfoDAO;
    private final StockDAO stockDAO;
    private final RelsService relsService;
    private final RelsValidator relsValidator;
    private final HttpServletResponse response;

    /** 출고등록 리스트 */
    @GetMapping
    public String rels(@ModelAttribute("srchParams") RelsVO srchParams, Model model){

        commonProcess(model);
        System.out.println("srchParams :: " + srchParams);

        List<RelsVO> relsList = relsDAO.relsList(srchParams);
        model.addAttribute("relsList", relsList);

        return "rels/rels";
    }

    /** 출고등록 D 리스트 */
    @ResponseBody
    @GetMapping("relsDetail")
    public List<RelsVO> relsDetail(String keyVal) {

        List<RelsVO> relsDetList = relsDAO.relsDetailList(keyVal);

        return relsDetList;
    }

    /** 출고등록 S (할당) */
    @ResponseBody
    @GetMapping("relsSubDtail")
    public List<RelsVO> relsSubDtail(String keyVal) {

        List<RelsVO> relsSubDetList = relsDAO.relsSubDetList(keyVal);

        return relsSubDetList;
    }


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

        relsVO.setUpdateYn("Y");
        model.addAttribute("relsVO", relsVO);
        // dblClick ** relsDNO 있음
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

        relsVO = relsDAO.relsDetInsert(keyVal);

        relsVO.setRegInAmt(0L);
        relsVO.setRelsCnt(0L);
        model.addAttribute("relsVO", relsVO);
        // checkBox ** relsDNO 없음
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
        model.addAttribute("relsVO", relsVO);

        return "rels/popup/relsPop";
    }

    /** 출고등록 추가 & 수정 처리  */
    @PostMapping("/relsSave")
    public String relsPs(@Valid RelsVO relsVO, Errors errors, Model model) {

        System.out.println("save Controller == " + relsVO);
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

        System.out.println("relsVO ===== ");
        System.out.println(relsVO);
        System.out.println("relsVO ===== ");
        if(relsVO.getUpdateYn().equals("Y")){
            System.out.println("update =====");
            relsService.update(relsVO);
        }else {
            relsService.save(relsVO);
        }

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

    /** 출고 할당 */
    @GetMapping("/relsAllotment")
    public String relsAllotment(String keyVal, Model model) {

        RelsVO relsVO = new RelsVO();

        String[] confKey = keyVal.split(",");

        // confKey 값으로 출고확정 진행
        for(int i = 0; i < confKey.length; i++) {

            // detail itemCd, clntCd data 조회
            List<RelsVO> detailInfo = relsDAO.relsDetailList(confKey[i]);

            // detail size만큼 itemCd, clntCd 데이터로 재고테이블 조회
            for(int j = 0; j < detailInfo.size(); j++){
                String itemCd = detailInfo.get(j).getItemCd();  // 상품코드
                String clntCd = detailInfo.get(j).getClntCd();  // 고객사 코드

                LocalDate relsDt = detailInfo.get(j).getRelsDt();   // 출고일자
                Long relsNo = detailInfo.get(j).getRelsNo();        // 출고번호
                Long relsDNo = detailInfo.get(j).getRelsDNo();      // 출고순번

                Long regInAmt = detailInfo.get(j).getRegInAmt();    // 출고지시수량


                TmstkVO tmstkVO = stockDAO.stockChk(itemCd, clntCd);

                // 재고 확인
                if(tmstkVO != null){

                    relsVO.setRelsDt(relsDt);
                    relsVO.setRelsNo(relsNo);
                    relsVO.setRelsDNo(relsDNo);
                    relsVO.setItemCd(itemCd);
                    relsVO.setClntCd(clntCd);
                    relsVO.setWactrCd(tmstkVO.getWactr_cd());
                    relsVO.setLocCd(tmstkVO.getLoc_cd());

                    do{
                        // 출고지시수량이 재고수량보다 작거나 같을경우
                        if(tmstkVO.getStock_amt() != 0 && regInAmt <= tmstkVO.getStock_amt()){
                            relsVO.setAlloAmt(regInAmt);
                            relsVO.setAlloAfterStock(tmstkVO.getStock_amt() - regInAmt);
                            if((regInAmt - tmstkVO.getStock_amt()) <= 0){
                                regInAmt = 0L;
                            }
                        }else if(tmstkVO.getStock_amt() != 0 && regInAmt > tmstkVO.getStock_amt()){
                            //  출고지시수량이 재고수량보다 클경우
                            relsVO.setAlloAmt(tmstkVO.getStock_amt());
                            relsVO.setAlloAfterStock(0L);
                            
                            regInAmt -= tmstkVO.getStock_amt();
                        }

                        // 재고가 있는 로케이션으로 셋팅
                        TmstkVO nextLoc = stockDAO.stockChk(itemCd, clntCd);
                        String locCd = nextLoc.getLoc_cd();

                        relsVO.setLocCd(locCd);


                        System.out.println(regInAmt);
                        relsVO.setRegNm("SESSION");
                        relsDAO.relsSinsert(relsVO);
                    } while (regInAmt != 0L); // do while
                } // if 재고확인
            } // for
            relsDAO.alloConf(confKey[i]);
        } // for
        return "rels/rels";
    }

    /** 출고확정(재고반영) */
    @GetMapping("/relsConf")
    public String relsConf(String keyVal, Model model) {

        String[] keyData = keyVal.split(",");
        System.out.println("keyData.length :: " + keyData.length);

        for(int i = 0; i < keyData.length; i++){
            // 출고수량 추출
            List<RelsVO> relsCnt = relsDAO.relsCnt(keyData[i]);
            System.out.println(relsCnt.size());

            System.out.println("keyData[i] :: "+ keyData[i]);
            // 출고대기 진행상태 확인
            String statsChk = relsDAO.statsChk(keyData[i]);
            if(statsChk.equals("03")){
                // 재고반영
                for(int j = 0; j < relsCnt.size(); j++) {
                    String wactrCd = String.valueOf(relsCnt.get(j).getWactrCd());
                    String clntCd = String.valueOf(relsCnt.get(j).getClntCd());
                    String itemCd = String.valueOf(relsCnt.get(j).getItemCd());
                    String locCd = String.valueOf(relsCnt.get(j).getLocCd());
                    String alloAmt = String.valueOf(relsCnt.get(j).getAlloAmt());

                    System.out.println(wactrCd + " : " + clntCd + " : " + itemCd + " : " + locCd + " : " + alloAmt);

                    stockDAO.relsConf(wactrCd, clntCd, itemCd, locCd, alloAmt);

                } // for
                relsDAO.relsConf(keyData[i]);
            } // if
        } // for
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
