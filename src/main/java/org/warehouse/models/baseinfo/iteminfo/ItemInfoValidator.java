package org.warehouse.models.baseinfo.iteminfo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.warehouse.configs.models.mapper.ClntDAO;
import org.warehouse.configs.models.mapper.ItemInfoDAO;
import org.warehouse.configs.models.mapper.WactrDAO;
import org.warehouse.models.admin.clnt.ClntVO;
import org.warehouse.models.baseinfo.wactr.WactrVO;

@Component
@RequiredArgsConstructor
public class ItemInfoValidator implements Validator {

    private final WactrDAO wactrDAO;
    private final ClntDAO clntDAO;
    private final ItemInfoDAO itemInfoDAO;


    @Override
    public boolean supports(Class<?> clazz) {
        return ItemInfoVO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        /**
         * 1. 물류센터코드 존재여부 확인
         * 2. 고객사코드 존재여부 확인
         * 3. 로케이션 존재여부 확인
         * 4. 상품코드 중복여부 확인
         * 5. 파렛트당 박스 수 음수 및 자릿수 확인(100,000 개 미만)
         */
        ItemInfoVO itemInfoVO = (ItemInfoVO) target;;


        // 물류센터
        String wactrCd = itemInfoVO.getWactrCd();
        String wactrNm = itemInfoVO.getWactrNm();
        // 고객사
        String clntCd = itemInfoVO.getClntCd();
        String clntNm = itemInfoVO.getClntNm();
        // 관리단위
        String boxUnit = itemInfoVO.getBoxUnit();

        WactrVO wactrChk = null;
        if(wactrCd != "" && wactrCd != null
                && wactrNm != "" && wactrNm != null){
            wactrChk = wactrDAO.getWactrByCdAndNm(wactrCd, wactrNm);
        }else {
            errors.rejectValue("wactrCd", "Validation.notExist.wactr");
        }
        ClntVO clntChk = clntDAO.getClntByCdAndNm(clntCd, clntNm);
        ItemInfoVO itemChk = itemInfoDAO.getItemEqualsChk(itemInfoVO);

        /** 물류센터코드 존재여부 확인 */
        if(wactrChk == null) {
            errors.rejectValue("wactrCd", "Validation.notExist.wactr");
        }

        /** 2. 고객사코드 존재여부 확인 */
        if(clntChk == null) {
            errors.rejectValue("clntCd", "Validation.notExist.clnt");
        }

        /** 4. 상품코드 중복여부 확인 */
        if(itemInfoVO.getUpdYn() == null && itemChk != null){
            errors.rejectValue("itemCd", "Validation.itemEquals");
        }

        /** 5. 파렛트당 박스 수 음수 및 자릿수 확인(100,000 개 미만) */
        if(itemInfoVO.getPltInBox() == null) {
            itemInfoVO.setPltInBox(0L);
        }else if (itemInfoVO.getPltInBox() < 0) {
            errors.rejectValue("pltInBox", "Validation.minusCheck");
        }else if (itemInfoVO.getPltInBox() > 100000) {
            errors.rejectValue("pltInBox", "Validation.maxCheck");
        }
    }
}