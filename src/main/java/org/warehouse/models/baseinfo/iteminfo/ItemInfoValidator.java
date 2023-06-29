package org.warehouse.models.baseinfo.iteminfo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.warehouse.configs.models.mapper.ClntDAO;
import org.warehouse.configs.models.mapper.ItemInfoDAO;

@Component
@RequiredArgsConstructor
public class ItemInfoValidator implements Validator {

    private final ItemInfoDAO itemInfoDAO;
    private final ClntDAO clntDAO;
    @Override
    public boolean supports(Class<?> clazz) {
        return ItemInfoVO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        System.out.println("validate11 :: " + target);

        /**
         * 1. 물류센터코드 존재여부 확인
         * 2. 고객사코드 존재여부 확인
         * 3. 로케이션 존재여부 확인
         * 3. 상품코드 중복여부 확인
         * 4. 파렛트당 박스 수 음수 및 자릿수 확인(100,000 개 미만)
         */
        System.out.println("======== 1 ========");
        ItemInfoVO itemInfoVO = (ItemInfoVO) target;

        ItemInfoVO wactrChk = itemInfoDAO.getWactrByCdAndNm(itemInfoVO);
        ItemInfoVO clntChk = itemInfoDAO.getClntByCdAndNm(itemInfoVO);
        ItemInfoVO LocChk = itemInfoDAO.getLocByCd(itemInfoVO);
        ItemInfoVO itemChk = itemInfoDAO.getItemEqualsChk(itemInfoVO);

        System.out.println("======== 4 ======== " + wactrChk);
        /** 물류센터코드 존재여부 확인 */
        System.out.println("wactrChk ::::::::::::::: " + wactrChk);
        if(wactrChk == null) {
            errors.rejectValue("wactrCd", "Validation.notExist.wactr");
        }

        /** 2. 고객사코드 존재여부 확인 */
        if(clntChk == null) {
            errors.rejectValue("clntCd", "Validation.notExist.clnt");
        }
        /** 3. 로케이션 존재여부 확인 */
        if(LocChk == null) {
            errors.rejectValue("locCd", "Validation.notExist.loc");
        }

        /** 4. 상품코드 중복여부 확인 */
        if(itemChk != null){
            errors.rejectValue("itemCd", "Validation.itemEquals");
        }

        /** 5. 파렛트당 박스 수 음수 및 자릿수 확인(100,000 개 미만) */
        if(itemInfoVO.getPltInBox() == null) {
            itemInfoVO.setPltInBox(0L);
        }else if (itemInfoVO.getPltInBox() < 0) {
            errors.rejectValue("pltInBox", "Validation.minusCheck");
        }
    }
}
