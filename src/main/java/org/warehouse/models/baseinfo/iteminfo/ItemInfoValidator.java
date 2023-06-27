package org.warehouse.models.baseinfo.iteminfo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.warehouse.controllers.admins.JoinForm;
import org.warehouse.models.baseinfo.iteminfo.ItemInfoVO;

@Component
@RequiredArgsConstructor
public class ItemInfoValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ItemInfoVO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        System.out.println("validate :: " + target);

        /**
         * 1. 상품코드 중복여부 확인
         * 2. 파렛트당 박스 수 음수 확인
         */
    }
}
