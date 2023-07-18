package org.warehouse.models.rels.relsAction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class RelsValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return RelsVO.class.isAssignableFrom(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {

        System.out.println("validation init =====");
        RelsVO relsVO = (RelsVO) target;

        if(relsVO.getRelsDt() == null)
            errors.rejectValue("relsDt", "Validation.null.relsDt");
        if(relsVO.getClntCd() == null || relsVO.getClntCd().isBlank())
            errors.rejectValue("clntCd", "Validation.null.clntCd");
        if(relsVO.getCustCd() == null || relsVO.getCustCd().isBlank())
            errors.rejectValue("custCd", "Validation.null.custCd");
        if(relsVO.getCustCtrCd() == null || relsVO.getCustCtrCd().isBlank())
            errors.rejectValue("custCtrCd", "Validation.null.custCtrCd");
        if(relsVO.getItemCd() == null || relsVO.getItemCd().isBlank())
            errors.rejectValue("itemCd", "Validation.null.itemCd");

        System.out.println("validation end =====");
    }
}
